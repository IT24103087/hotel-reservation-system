package com.example.newhotelsystem.config;

import com.example.newhotelsystem.model.StaffRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Custom converter to convert String to StaffRole enum.
 * This delegates to the fromString method in the StaffRole enum.
 */
@Component
public class StringToStaffRoleConverter implements Converter<String, StaffRole> {

    @Override
    public StaffRole convert(String source) {
        return StaffRole.fromString(source);
    }
}
