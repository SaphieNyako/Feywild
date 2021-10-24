package com.feywild.feywild.config.validator;

import com.feywild.feywild.config.data.StructureData;
import io.github.noeppi_noeppi.libx.config.ConfigValidator;

import java.util.Optional;

public class StructureDataValidator implements ConfigValidator<StructureData, StructureCfg> {

    @Override
    public Class<StructureData> type() {
        return StructureData.class;
    }

    @Override
    public Class<StructureCfg> annotation() {
        return StructureCfg.class;
    }

    @Override
    public Optional<StructureData> validate(StructureData value, StructureCfg validator) {
        if (value.minimum_distance() >= value.average_distance()) {
            return Optional.of(new StructureData(value.minimum_distance(), value.minimum_distance() + 1));
        }
        return Optional.empty();
    }
}
