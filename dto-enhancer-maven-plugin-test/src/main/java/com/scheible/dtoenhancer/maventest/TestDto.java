package com.scheible.dtoenhancer.maventest;

import com.scheible.dtoenhancer.api.AutoDto;
import java.util.Objects;

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

    public void setValue(int value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && getClass() != obj.getClass()) {
            TestDto other = (TestDto) obj;
            if (value != other.value) {
                return false;
            }
            if (!Objects.equals(text, other.text)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, text);
    }
}
