package ru.kata.spring.boot_security.demo.model;

import ru.kata.spring.boot_security.demo.validation.UniqLogin;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "users")
@UniqLogin(message = "Пользователь с таким логином уже зарегистрирован в системе")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty (message = "Имя должно быть не пустое")
    @Size(min=2, max=100, message = "Не меньше 2 и не больше 100-ти знаков")
    @Pattern(regexp = "[A-Za-zА-ЯЁа-яё]+", message = "Введено некорректное имя: ${validatedValue}")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "Фамилия не может быть пустой")
    @Size(min=2, max=100, message = "Не меньше 2 и не больше 100-ти знаков")
    @Pattern(regexp = "[A-Za-zА-ЯЁа-яё]+", message = "Введена некорректная фамилия: ${validatedValue}")
    private String lastName;

    @Column(name = "age")
    @NotNull(message = "Возраст должен быть заполнен")
    @DecimalMax(message = "Введите корректный возраст",
            value = "150")
    @DecimalMin(message = "Введите корректный возраст",
            value = "0", inclusive = false)
    private Byte age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "login", unique = true)
    @NotEmpty(message = "Логин должен быть заполнен")
    @Pattern(regexp = "[A-Za-z0-9_]+", message = "Логин может состоять из букв латинского алфавита, цифр и символа подчёркивания")
    @Size(min=3, max=20, message = "Не меньше 3 и не больше 20-ти знаков")
    //@UniqLogin(message = "Пользователь с таким логином уже зарегистрирован в системе")
    private String username;

    @NotEmpty(message = "Пароль должен быть заполнен")
    @Size(min=3, message = "Не меньше 3-х знаков")
    @Column(name = "password")
    private String password;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

