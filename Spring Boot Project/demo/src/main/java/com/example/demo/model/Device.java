package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int battery_power;  // changed from batteryPower
    private int blue;
    private float clock_speed;  // changed from clockSpeed
    private int dual_sim;       // changed from dualSim
    private int fc;
    private int four_g;        // changed from fourG
    private int int_memory;    // changed from intMemory
    private float m_dep;       // changed from mDep
    private int mobile_wt;     // changed from mobileWt
    private int n_cores;       // changed from nCores
    private int pc;
    private int px_height;     // changed from pxHeight
    private int px_width;      // changed from pxWidth
    private int ram;
    private float sc_h;        // changed from scH
    private float sc_w;        // changed from scW
    private int talk_time;     // changed from talkTime
    private int three_g;       // changed from threeG
    private int touch_screen;   // changed from touchScreen
    private int wifi;
    private int predicted_price_range;  // changed from predictedPriceRange

    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBattery_power() {
        return battery_power;
    }

    public void setBattery_power(int battery_power) {
        this.battery_power = battery_power;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public float getClock_speed() {
        return clock_speed;
    }

    public void setClock_speed(float clock_speed) {
        this.clock_speed = clock_speed;
    }

    public int getDual_sim() {
        return dual_sim;
    }

    public void setDual_sim(int dual_sim) {
        this.dual_sim = dual_sim;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public int getFour_g() {
        return four_g;
    }

    public void setFour_g(int four_g) {
        this.four_g = four_g;
    }

    public int getInt_memory() {
        return int_memory;
    }

    public void setInt_memory(int int_memory) {
        this.int_memory = int_memory;
    }

    public float getM_dep() {
        return m_dep;
    }

    public void setM_dep(float m_dep) {
        this.m_dep = m_dep;
    }

    public int getMobile_wt() {
        return mobile_wt;
    }

    public void setMobile_wt(int mobile_wt) {
        this.mobile_wt = mobile_wt;
    }

    public int getN_cores() {
        return n_cores;
    }

    public void setN_cores(int n_cores) {
        this.n_cores = n_cores;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getPx_height() {
        return px_height;
    }

    public void setPx_height(int px_height) {
        this.px_height = px_height;
    }

    public int getPx_width() {
        return px_width;
    }

    public void setPx_width(int px_width) {
        this.px_width = px_width;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public float getSc_h() {
        return sc_h;
    }

    public void setSc_h(float sc_h) {
        this.sc_h = sc_h;
    }

    public float getSc_w() {
        return sc_w;
    }

    public void setSc_w(float sc_w) {
        this.sc_w = sc_w;
    }

    public int getTalk_time() {
        return talk_time;
    }

    public void setTalk_time(int talk_time) {
        this.talk_time = talk_time;
    }

    public int getThree_g() {
        return three_g;
    }

    public void setThree_g(int three_g) {
        this.three_g = three_g;
    }

    public int getTouch_screen() {
        return touch_screen;
    }

    public void setTouch_screen(int touch_screen) {
        this.touch_screen = touch_screen;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }

    public int getPredicted_price_range() {
        return predicted_price_range;
    }

    public void setPredicted_price_range(int predicted_price_range) {
        this.predicted_price_range = predicted_price_range;
    }
}
