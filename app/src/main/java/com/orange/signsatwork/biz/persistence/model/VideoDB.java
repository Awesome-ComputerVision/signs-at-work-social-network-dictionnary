package com.orange.signsatwork.biz.persistence.model;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class VideoDB {
  // An autogenerated id (unique for each video in the db)
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long idForName;

  @NotNull
  private String url;

  @NotNull
  private Date createDate;

  private String pictureUri;

  private long nbView;

  private long averageRate;

  private long nbComment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="sign_id")
  private SignDB sign;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private UserDB user;

  @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
  private List<CommentDB> comments = new ArrayList<>();

  @OneToMany(mappedBy = "primaryKey.video", fetch = FetchType.LAZY)
  private List<RatingDB> ratings = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name="associate_video",
    joinColumns={@JoinColumn(name="video_id")},
    inverseJoinColumns={@JoinColumn(name="associate_video_id")})
  private List<VideoDB> associates;

  @ManyToMany(mappedBy="associates", fetch = FetchType.LAZY)
  private List<VideoDB> referenceBy;

  @ManyToMany(mappedBy = "videos", fetch = FetchType.LAZY)
  @JsonBackReference
  private List<FavoriteDB> favorites;


  public VideoDB(String url, Date createDate) {
    this.url = url;
    this.createDate = createDate;
  }
}
