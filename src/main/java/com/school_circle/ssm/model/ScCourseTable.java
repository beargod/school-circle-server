package com.school_circle.ssm.model;


public class ScCourseTable {
    /**
     * 第几周
     */
    private int week;
    /**
     * 星期几
     */
    private int weekday;
    /**
     * 第几节课就开始
     */
    private int lesson;
    /**
     * 课程名字
     */
    private String name;
    /**
     * 老师名字
     */
    private String teacher;
    /**
     * 教室地址
     */
    private String classroom;
    /**
     * 课程时长
     */
    private int classDuration;

    private long userId;

    private long id;

    public ScCourseTable() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getClassDuration() {
        return classDuration;
    }

    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public void setClassDuration(int classDuration) {
        this.classDuration = classDuration;
    }

    public ScCourseTable clone(){
        ScCourseTable result = new ScCourseTable();
        result.setWeekday(weekday);
        result.setWeek(week);
        result.setClassDuration(classDuration);
        result.setClassroom(classroom);
        result.setLesson(lesson);
        result.setTeacher(teacher);
        result.setName(name);
        return result;
    }}