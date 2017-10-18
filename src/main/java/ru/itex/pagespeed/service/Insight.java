/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itex.pagespeed.service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itex.entity.AddressEntity;
import ru.itex.repository.OptionRepository;
import ru.itex.vo.ResultVO;
import ru.itex.entity.ResultEntity;
import ru.itex.repository.AddressRepository;
import ru.itex.repository.ResultRepository;
import javax.script.*;

/**
 *
 * @author Admin
 */
@Service
public class Insight {

    private final String GOOGLE_API_PAGE_INSIGHT = "https://www.googleapis.com/pagespeedonline/v2/runPagespeed?url=";

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ResultStore resultStore;

    public String fetch(String url, String type) throws Exception {
        int ch;
        String text = "";
        URLConnection connection = new URL(GOOGLE_API_PAGE_INSIGHT + url + "&strategy=" + type).openConnection();
        InputStream is = connection.getInputStream();
        while ((ch = is.read()) != -1) {
            text += (char) ch;
        }
        return text;
    }

    class InsightThread implements Runnable {

        private String url;

        public InsightThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                String map = this.url;
                System.out.println(map + " : Старт");

                String dataDesktop = fetch(map, "desktop");
                java.util.List<Integer> lastDesktopScores = resultRepository.getLastScores(resultStore.getIdByUrl(map), "desktop");
                lastDesktopScores.add(getScore(dataDesktop));
                System.out.println(map + " : Версия для компьютера " + lastDesktopScores.get(lastDesktopScores.size()-1));

                String dataMobile = fetch(map, "mobile");
                java.util.List<Integer> lastMobileScores = resultRepository.getLastScores(resultStore.getIdByUrl(map), "mobile");
                lastMobileScores.add(getScore(dataMobile));
                System.out.println(map + " : Версия для телефона " + lastMobileScores.get(lastMobileScores.size()-1));

                //сохраняем в бд
                ResultEntity desktopResult = new ResultEntity();
                desktopResult.setAddressId(resultStore.getIdByUrl(map));
                desktopResult.setDate(new java.sql.Timestamp(new Date().getTime()));
                desktopResult.setRawData(dataDesktop);
                desktopResult.setScore(getScore(dataDesktop));
                desktopResult.setType("desktop");

                ResultEntity mobileResult = new ResultEntity();
                mobileResult.setAddressId(resultStore.getIdByUrl(map));
                mobileResult.setDate(new java.sql.Timestamp(new Date().getTime()));
                mobileResult.setRawData(dataMobile);
                mobileResult.setScore(getScore(dataMobile));
                mobileResult.setType("mobile");
                runScript(map, lastDesktopScores, "desktop");
                runScript(map, lastMobileScores, "mobile");
                try {
                    resultRepository.save(desktopResult);
                    resultRepository.save(mobileResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                Logger.getLogger(Insight.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void fetchAll() throws Exception {
        for (int i = 0; i < getURL().size(); i++) {
            Thread r = new Thread(new InsightThread((String) getURL().get(i)));
            r.start();
        }
    }

    private Integer getScore(String data) throws Exception {
        final JSONObject jsonObject = new JSONObject(data);
        return jsonObject.getJSONObject("ruleGroups").getJSONObject("SPEED").getInt("score");
    }

    private ArrayList getURL() throws Exception {
        ArrayList<String> urlTrip = new ArrayList();
        for (AddressEntity addressEntity : addressRepository.findAll()) {
            urlTrip.add(addressEntity.getUrl());
        }
        return urlTrip;
    }

    public ArrayList<ResultVO> getResultsByPeriod(String period_begin, String period_end) {
        ArrayList<ResultVO> result = new ArrayList();

        return result;
    }

    public ArrayList resultByDate() throws Exception {
        ArrayList<Integer> byDate = new ArrayList();
        return byDate;
    }

    private void sendMail(String deviceType, String url, Integer currentScore, Integer lastScore, String message, Integer criticalValue) {
        String emailList = "";
        String subject = "Значительное снижене оценки.";

        if (optionRepository.getValue("emails") != null) {
            emailList = optionRepository.getValue("emails");
            if (optionRepository.getValue("subject") != null) {
                subject = optionRepository.getValue("subject");
            }

            if (message.isEmpty()) message = "Значение оценки "
                    + (deviceType.equals("desktop") ? "для десктопной" : "для мобильной")
                    + " версии сайта " + url + " упало в значительной степени!";
            else message += "\nСайт: " + url + "\nТекущая оценка: " + currentScore +
                    "\nПредыдущая оценка: " + lastScore + "\nКритическая отметка: " + criticalValue;

            ru.itex.ssl.Sender tlsSender = new ru.itex.ssl.Sender("r.haibullin@it-exclusive.ru", "haibullin");
            tlsSender.send(emailList, subject, message, url, deviceType);
        }
        else System.out.println("В настройках не указаны адреса получателей оповещений о критическом состоянии!");
    }

    private void runScript(String url, java.util.List<Integer> lastScores, String deviceType) throws ScriptException, NoSuchMethodException {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");

        scriptEngine.put("lastScores", lastScores);
        scriptEngine.put("deviceType", deviceType);
        scriptEngine.put("url", url);
        scriptEngine.put("defaultMessage", optionRepository.getValue("defaultMessage"));

        if (deviceType.equals("desktop")) {
            if (optionRepository.getValue("critical_value_for_desktop") != null) {
                scriptEngine.put("criticalValue", Integer.parseInt(optionRepository.getValue("critical_value_for_desktop").trim().replace("\n", "")));
            } else {
                scriptEngine.put("criticalValue", 0);
            }
        }
        else {
            if (optionRepository.getValue("critical_value_for_mobile") != null) {
                scriptEngine.put("criticalValue", Integer.parseInt(optionRepository.getValue("critical_value_for_mobile").trim().replace("\n", "")));
            } else {
                scriptEngine.put("criticalValue", 0);
            }
        }

        String script = optionRepository.getValue("policy");

        scriptEngine.eval(script);

        if ((Boolean) scriptEngine.get("criticalState")) {
            sendMail(deviceType, url, lastScores.get(lastScores.size()-1), lastScores.get(lastScores.size()-2), (String) scriptEngine.get("message"), (Integer) scriptEngine.get("criticalValue"));
        }
//        else System.out.println("Оценка для сайта " + url + " в пределах нормы.");
    }
}
