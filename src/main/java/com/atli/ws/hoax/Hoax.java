package com.atli.ws.hoax;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.atli.ws.file.FileAttachment;
import com.atli.ws.user.User;

import lombok.Data;

@Data
@Entity // Database
public class Hoax{
	private static final long serialVersionUID = -404791862213939362L;
	
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Her tablonunu kendi idsi
	private long id; //Primary key
	
	@Size(min=1, max=1000)
	@Column(length=1000)
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@ManyToOne
	private User user;
	
	
	/*
	@OneToOne(mappedBy="hoax",orphanRemoval = true)//Eğer ilgili hoax silinirse OneToOne olan da silinir.
	private FileAttachment fileAttachment;
	*/
	
	@OneToOne(mappedBy="hoax",cascade = CascadeType.REMOVE)//cascade
	private FileAttachment fileAttachment;

}
