# dto-enhancer

Inspired by the awesome [@AutoValue](https://github.com/google/auto/tree/master/value) but target to simple data transfer objects (DTOs) instead of value types.

All existing other solutions had some drawbacks:
- [(de)Lombok](https://projectlombok.org/) is pretty close but requires that the DTOs are placed in a separate source root (default /src/main/lombok) and uses a generated sources output folder
- [Joda-Beans](http://www.joda.org/joda-beans/) Maven plugin updates directly the Java source files but with a much more heavy bean concept and dedicated generated code sections in the Java files

## So what is dto-enhancer then?

Lombock's `@ToString`, `@EqualsAndHashCode`, `@Getter` and `@Setter` merged into `@DtoValue` with a code generation comparable to Joda-Beans but without the dedicated generated code section. Code generation also uses the overall workflow idea of Joda-Beans: a generate and verify goal. The verify goal makes the build fail if any missing generated code is found.

Hand writen class:
```java
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
```

DTO enhanced result:
```java
@AutoDto
public class TestDto {
    ----
}
```
