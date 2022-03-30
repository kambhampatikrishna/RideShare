package com.example.demo.Models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {

        @Id
        @GeneratedValue( strategy = GenerationType.IDENTITY)
        @Column(name = "userid", nullable = false)
        private Long userId;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String gender;

        @Column(nullable = false)
        private int age;

        private int taken_count ;

        private int offered_count ;

        public Long getUserID() {
            return userId;
        }

        public void setUserID(Long userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getTaken_count() {
            return taken_count;
        }

        public void setTaken_count(int taken_count) {
            this.taken_count = taken_count;
        }

        public int getOffered_count() {
            return offered_count;
        }

        public void setOffered_count(int offered_count) {
            this.offered_count = offered_count;
        }

        @Override
        public String toString() {
            return "User Added{" +
                    "name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", age=" + age +
                    ", taken_count=" + taken_count +
                    ", offered_count=" + offered_count +
                    '}';
        }
}
