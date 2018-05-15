package com.monyrama.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.monyrama.entity.DBConstants;
import com.monyrama.controller.UniqueID;
import com.monyrama.db.enumarations.EntityStates;

@MappedSuperclass
public abstract class BaseEntity {
	private Long id;
	private String name;
	private String comment;
	private Date lastChangeDate;
	private Character state;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = DBConstants.NAME_LENGTH)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	@Column(length = DBConstants.COMMENTS_LENGTH)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setState(Character state) {
		this.state = state;
	}

	public Character getState() {
		return state;
	}	
	
	@Column(nullable = false)
	public Date getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(Date lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	@Transient
	public boolean isActive() {
		return EntityStates.ACTIVE.getCode().equals(getState());
	}
	
	@Override
	public String toString() {
		return name;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public void prepareToPersist() {
		if(id == null) {
			id = UniqueID.get();
		}
		
		if(state == null) {
			state = EntityStates.ACTIVE.getCode();
		}		
		
		if(lastChangeDate == null) {
			lastChangeDate = Calendar.getInstance().getTime();	
		}
	}
}
