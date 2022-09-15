package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Objects;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "tbl_tags")
public class UserTest extends BaseEntity{
    private Long id;
    private String name;


}
