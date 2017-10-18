/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.itex.pagespeed.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itex.entity.OptionEntity;
import ru.itex.pagespeed.service.Insight;
import ru.itex.pagespeed.service.ResultStore;
import ru.itex.pagespeed.service.OptionStore;
import ru.itex.vo.OptionVO;
import ru.itex.vo.AddressVO;
import ru.itex.vo.ResultVO;


@RestController
@RequestMapping(value = "/api")
public class PageSpeed {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private Insight insight;
    
    @Autowired
    private ResultStore resultStore;

    @Autowired
    private OptionStore optionStore;

    @RequestMapping("/saveResult")
    public void saveResult(ResultVO resultVO) {
        resultStore.saveResult(resultVO);
    }

    @RequestMapping("/getAddressList")
    public List<AddressVO> getAddressList() {
        return resultStore.getAddressList();
    }

    @RequestMapping("/page-insight")
    public List<ResultVO> getResultByDate(@RequestParam("period_begin") String begin, @RequestParam("period_end") String end, @RequestParam("address_id") Long addressId) throws ParseException {
        return resultStore.getScoreByDate(begin, end, addressId);
    }

    @RequestMapping("/getAverageByDate")
    public Double getAverageByDate(@RequestParam("period_begin") String begin, @RequestParam("period_end") String end) throws ParseException {
        return resultStore.getAverage(begin, end);
    }
    
    @RequestMapping("/start")
    public String start(boolean answer) throws MalformedURLException, IOException, Exception {
        insight.fetchAll();
        return null;
    }

    @RequestMapping("/get_options")
    public List<OptionVO> getOptions() {
        return optionStore.getOptions();
    }

    @RequestMapping("/get_option")
    public OptionVO getOption(@RequestParam("key") String key) {
        return optionStore.getOption(key);
    }

    @RequestMapping("/get_value")
    public String getValue(@RequestParam("key") String key) {
        return optionStore.getValue(key);
    }

    @RequestMapping("/get_critical_value_for_desktop")
    public Integer getCriticalValue() {
        return Integer.parseInt(optionStore.getValue("critical_value_for_desktop"));
    }

    @RequestMapping("/get_last_scores")
    public List<Integer> getLastScores(@RequestParam("id") Long urlId, @RequestParam("type") String deviceType) {
        return resultStore.getLastScores(urlId, deviceType);
    }

 }