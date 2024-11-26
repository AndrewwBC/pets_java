package com.example.pets4ever.user;


import com.example.pets4ever.comment.Comment;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String id;

    @Length(min = 1, max = 128, message = "Nome completo deve ter de 3 a 128 caractéres")
    @NotEmpty(message = "Nome completo deve ser preenchido")
    private String fullname;

    @Length(max = 128, message = "No máximo 128 caractéres")
    private String bio;

    @Length(min = 3, max = 32, message = "Nome de usuário de 3 a 32 caractéres!")
    @NotEmpty(message = "Nome de usuário deve ser preenchido")
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @Email(message = "Email inválido!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email deve ser preenchido")
    private String email;

    @Length(min = 8,max = 64, message = "Senha com ao menos 8 caractéres!")
    private String password;

    private String profileImgUrl;

    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @ManyToMany(mappedBy = "likes", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> userLikes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_following",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> followingUsers;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "followingUsers")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> followedByUsers;

    public User(String fullname, String username, String email, String password, UserRole role) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String id, String fullname, String username, String email, String profileImgUrl, List<User> followers, List<User> following) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.followedByUsers = followers;
        this.followingUsers = following;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                ",posts," + posts +
                '}';
    }
}
