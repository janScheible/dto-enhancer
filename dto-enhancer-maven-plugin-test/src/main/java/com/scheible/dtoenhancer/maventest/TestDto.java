package com.scheible.dtoenhancer.maventest;

import com.scheible.dtoenhancer.api.AutoDto;

/**
 *
 * @author sj
 */
@AutoDto
public class TestDto {
    
    private int value;
    private String text;

    public TestDto() {
    }

    public TestDto(int value, String text) {
        this.value = value;
        this.text = text;
    }    
}
