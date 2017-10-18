package ru.itex.pagespeed.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itex.entity.AddressEntity;
import ru.itex.entity.ResultEntity;
import ru.itex.repository.AddressRepository;
import ru.itex.repository.ResultRepository;
import ru.itex.vo.AddressVO;
import ru.itex.vo.ResultVO;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by PC-020 on 19.01.2017.
 */
@Service
public class ResultStore {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private AddressRepository addressRepository;

    public AddressVO toAddressVo(AddressEntity addressEntity) {
        return new AddressVO(addressEntity.getId(), addressEntity.getUrl(), addressEntity.getDescription());
    }

    public ResultVO toResultVO(ResultEntity resultEntity, String address) {
        ResultVO resultVO = new ResultVO(resultEntity.getId(), resultEntity.getAddressId(), resultEntity.getScore(), resultEntity.getType(), resultEntity.getDate(), address);
        return resultVO;
    }

    public ResultEntity toResultEntity(ResultVO resultVO) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setId(resultVO.getId());
        resultEntity.setAddressId(resultVO.getAddressId());
        resultEntity.setDate(resultVO.getDate());
        resultEntity.setScore(resultVO.getScore());
        resultEntity.setType(resultVO.getType());
        resultEntity.setRawData(resultVO.getRawData());
        return resultEntity;
    }

    public AddressEntity toAddressEntity(AddressVO addressVO) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(addressVO.getId());
        addressEntity.setDescription(addressVO.getDescription());
        addressEntity.setUrl(addressVO.getUrl());
        return addressEntity;
    }

    public void saveAddress(AddressEntity addressEntity) {
        addressRepository.save(addressEntity);
    }

    public void saveResult(ResultVO resultVO) {
        resultRepository.save(toResultEntity(resultVO));
    }

    public List<AddressVO> getAddressList() {
        List<AddressVO> addressVOList = new ArrayList<>();
        for (AddressEntity addressEntity : addressRepository.findAll()) {
            addressVOList.add(toAddressVo(addressEntity));
        }
        return addressVOList;
    }

    public List<ResultVO> getScoreByDate(String begin, String end, Long addressId) throws ParseException {
        List<ResultVO> scores = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date beginDate = simpleDateFormat.parse(begin);
        Date endDate = simpleDateFormat.parse(end);
        for (ResultEntity resultEntity : resultRepository.getByPeriod(beginDate, endDate, addressId)) {
            scores.add(toResultVO(resultEntity, getUrlById(resultEntity.getAddressId())));
        }
        return scores;
    }

    public Double getAverage(String begin, String end) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date beginDate = simpleDateFormat.parse(begin);
        Date endDate = simpleDateFormat.parse(end);
        return resultRepository.getAverageByDate(beginDate, endDate);
    }

    public List<String> getUrlList() {
        List<String> urlList = new ArrayList<>();
        for (AddressEntity addressEntity : addressRepository.findAll()) {
            urlList.add(addressEntity.getUrl());
        }
        return urlList;
    }

    public List<Long> getIdList() {
        List<Long> idList = new ArrayList<>();
        for (AddressEntity addressEntity : addressRepository.findAll()) {
            idList.add(addressEntity.getId());
        }
        return idList;
    }

    public String getUrlById(Long id) {
        AddressEntity addressEntity = addressRepository.findOne(id);
        if (addressEntity != null) {
            return addressEntity.getUrl();
        }
        return null;
    }

    public Long getIdByUrl(String url) {
        return resultRepository.getIdByUrl(url);
    }

    public List<Integer> getLastScores(Long urlId, String deviceType) {
        List<Integer> scoresList = resultRepository.getLastScores(urlId, deviceType);
        return scoresList.subList(scoresList.size() - 9, scoresList.size());
    }

    /*public String getDataForId(Long id) {
        ResultEntity resultEntity = resultRepository.findOne(id);
        if (resultEntity != null) {
            String rawDataString = resultEntity.getRawData().toString();
            while (rawDataString.contains("  ")) {
                String replace = rawDataString.replace("  ", " ");
                rawDataString = replace;
            }
            rawDataString = rawDataString.replace("\n", "");
            rawDataString = rawDataString.replace("\\\"", "'");
            return rawDataString;
        }
        return null;
    }*/
}