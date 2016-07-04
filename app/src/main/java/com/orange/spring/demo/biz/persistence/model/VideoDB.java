package com.orange.spring.demo.biz.persistence.model;

/*
 * #%L
 * Signs at work
 * %%
 * Copyright (C) 2016 Orange
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// we want to save 'Video' objects in the 'videos' DB table
@Table(name = "videos")
@Entity
// default constructor only exists for the sake of JPA
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideoDB {
  // An autogenerated id (unique for each video in the db)
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String url;

  @NotNull
  private Date createDate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="sign_id")
  private SignDB sign;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="user_id")
  private UserDB user;

  @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
  private List<CommentDB> comments = new ArrayList<>();

  @OneToMany(mappedBy = "primaryKey.video", cascade = CascadeType.ALL)
  private List<RatingDB> ratings = new ArrayList<>();

  public VideoDB(String url, Date createDate) {
    this.url = url;
    this.createDate = createDate;
  }
}
