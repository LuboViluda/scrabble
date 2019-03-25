package com.srable.corporation.scrabble;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Player data transfer object.
 * Created by lubomir.viluda on 3/23/2019.
 */
public class PlayerDto {
    private String fullName;
    private String href;
    @JsonInclude(NON_NULL)
    private Map<String, Number> statistics;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Map<String, Number> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Number> statistics) {
        this.statistics = statistics;
    }
}
