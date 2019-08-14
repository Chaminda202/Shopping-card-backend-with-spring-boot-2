package com.spring.ehcache.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_extension")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageExtension implements Serializable{
	private static final long serialVersionUID = -2525860844599874153L;
	@Id
    private String extention;
    private String description;
}
