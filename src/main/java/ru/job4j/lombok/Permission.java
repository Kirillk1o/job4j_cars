package ru.job4j.lombok;

import lombok.*;

import java.util.List;
@Builder(builderMethodName = "of")
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permission {
    private int id;
    @EqualsAndHashCode.Include
    private String name;
    @Singular("accessBy")
    private List<String> rules;
}
