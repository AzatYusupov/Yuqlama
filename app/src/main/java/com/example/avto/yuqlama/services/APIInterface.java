package com.example.avto.yuqlama.services;

import com.example.avto.yuqlama.models.Group;
import com.example.avto.yuqlama.models.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by AzatYusupov on 25.01.2018.
 */

public interface APIInterface {

    @GET("/groups")
    Call<List<Group>> doGetGroupList();

    @GET("/students")
    Call<List<Student>> doGetStudentList();

}
