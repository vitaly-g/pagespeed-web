package ru.itex.pagespeed.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itex.repository.OptionRepository;
import ru.itex.entity.OptionEntity;
import ru.itex.vo.OptionVO;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devil4lived@gmail.com on 24.01.2017.
 */
@Service
public class OptionStore {

    @Autowired
    private OptionRepository optionRepository;

    public OptionVO toOptionVO(OptionEntity optionEntity) {
        return new OptionVO(optionEntity.getId(), optionEntity.getKey(), optionEntity.getValue());
    }

    public OptionEntity toOptionEntity(OptionVO optionVO) {
        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setId(optionVO.getId());
        optionEntity.setKey(optionVO.getKey());
        optionEntity.setValue(optionVO.getValue());
        return optionEntity;
    }

    public List<OptionVO> getOptions() {
        List<OptionVO> optionVOList = new ArrayList<>();
        for (OptionEntity optionEntity : optionRepository.findAll()) {
            optionVOList.add(toOptionVO(optionEntity));
        }
        return optionVOList;
    }

    public OptionVO getOption(String key) {
        return optionRepository.getOption(key);
    }

    public String getValue(String key) {
        if (optionRepository.getValue(key) != null) {
            return optionRepository.getValue(key).trim().replace("\n", "");
        }
        return "null";
    }
}
