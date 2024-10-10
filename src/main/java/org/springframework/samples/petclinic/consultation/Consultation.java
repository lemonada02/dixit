package org.springframework.samples.petclinic.consultation;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consultations")
@Getter
@Setter
public class Consultation extends BaseEntity {

	@Column(name = "title")
	@NotEmpty
	private String title;

	@Column(name = "is_clinic_comment")
	@NotNull
	private Boolean isClinicComment = false;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private ConsultationStatus status;

	@ManyToOne
	@JoinColumn(name = "owner_id")	
	@NotNull
	private Owner owner;

	@Column(name = "creation_date")
	@CreationTimestamp
	private LocalDateTime creationDate;

}
