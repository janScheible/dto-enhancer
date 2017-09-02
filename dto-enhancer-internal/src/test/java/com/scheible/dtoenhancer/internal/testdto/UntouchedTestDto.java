package com.scheible.dtoenhancer.internal.testdto;

import com.scheible.dtoenhancer.api.AutoDto;
import java.util.List;

/**
 *
 * @author sj
 */
@AutoDto
public class UntouchedTestDto {
    
    private int numericValue;
    private String string;
    private List<String> stringValues;

    public UntouchedTestDto() {
    }

    public UntouchedTestDto(int numericValue, String string, List<String> stringValues) {
        this.numericValue = numericValue;
        this.string = string;
        this.stringValues = stringValues;
    }
}
