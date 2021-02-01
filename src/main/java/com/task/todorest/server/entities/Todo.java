package com.task.todorest.server.entities;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Model
@Table(name="todo")
public class Todo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8072205377595150002L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	Long date;
	
	String itemName;
	String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder();
        builder.append("Todo{id=").append(id).append(", name=")
                .append(itemName).append(", description=")
                .append(description).append(", date=")
                .append(date).append("}");
        
        return builder.toString();
    }

}
