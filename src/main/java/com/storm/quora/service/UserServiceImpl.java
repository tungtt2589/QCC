package com.storm.quora.service;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.storm.quora.dto.UserDTO;
import com.storm.quora.model.User;
import com.storm.quora.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository repository;

    @Override
    public User findByName(String username) throws Exception {
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getUserByUsername")
                .header("content-type", "application/json")
                .body("{\n \"username\":\"" + username + "\"\n}\n").asJson();
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        if (results.length() > 0) {
            JSONObject jsonObject = results.getJSONObject(0);
            User user = new User();
            if (!jsonObject.isNull("user_id")) {
                user.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("name")) {
                user.setName(jsonObject.getString("name"));
            }
            if (!jsonObject.isNull("username")) {
                user.setUsername(jsonObject.getString("username"));
            }
            if (!jsonObject.isNull("password")) {
                user.setPassword(jsonObject.getString("password"));
            }
            if (!jsonObject.isNull("email")) {
                user.setEmail(jsonObject.getString("email"));
            }
            if (!jsonObject.isNull("phone_number")) {
                user.setPhoneNumber(jsonObject.getString("phone_number"));
            }
            if (!jsonObject.isNull("dob")) {
                user.setDob(jsonObject.getString("dob"));
            }
            if (!jsonObject.isNull("gender")) {
                user.setGender(jsonObject.getString("gender"));
            }
            if (!jsonObject.isNull("address")) {
                user.setAddress(jsonObject.getString("address"));
            }
            if (!jsonObject.isNull("job")) {
                user.setJob(jsonObject.getString("job"));
            }
            if (!jsonObject.isNull("created_time")) {
                user.setCreatedTime(jsonObject.getString("created_time"));
            }
            if (!jsonObject.isNull("edited_time")) {
                user.setEditedTime(jsonObject.getString("edited_time"));
            }
            if (!jsonObject.isNull("avatar")) {
                user.setAvatar(jsonObject.getString("avatar"));
            }
            if (!jsonObject.isNull("role")) {
                user.setRole(jsonObject.getLong("role"));
            }
            if (!jsonObject.isNull("last_activity_time")) {
                user.setLastActivityTime(jsonObject.getString("last_activity_time"));
            }
            if (!jsonObject.isNull("user_status")) {
                user.setUserStatus(jsonObject.getLong("user_status"));
            }
//            logger.info(new Gson().toJson(user));
            return user;
        }
        return null;
    }

    @Override
    public UserDTO register(UserDTO dto) throws Exception {
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/signup")
                .header("content-type", "application/json")
                .body("{\n\"name\":\"" + dto.getName() + "\"," +
                        "\n\"email\": \"" + dto.getEmail() + "\"," +
                        "\n\"username\": \"" + dto.getUsername() + "\"," +
                        "\n\"google_id\": \"" + dto.getGoogleId() + "\"," +
                        "\n\"password\": \"" + dto.getPassword() + "\"\n}")
                .asJson();
        UserDTO userDTO = new UserDTO();
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        if (results.length() > 0) {
            JSONObject jsonObject = results.getJSONObject(0);
            if (!jsonObject.isNull("user_id")) {
                userDTO.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("name")) {
                userDTO.setName(jsonObject.getString("name"));
            }
            if (!jsonObject.isNull("username")) {
                userDTO.setUsername(jsonObject.getString("username"));
            }
            if (!jsonObject.isNull("password")) {
                userDTO.setPassword(jsonObject.getString("password"));
            }
            if (!jsonObject.isNull("email")) {
                userDTO.setEmail(jsonObject.getString("email"));
            }
            if (!jsonObject.isNull("phone_number")) {
                userDTO.setPhoneNumber(jsonObject.getString("phone_number"));
            }
            if (!jsonObject.isNull("dob")) {
                userDTO.setDob(jsonObject.getString("dob"));
            }
            if (!jsonObject.isNull("gender")) {
                userDTO.setGender(jsonObject.getString("gender"));
            }
            if (!jsonObject.isNull("address")) {
                userDTO.setAddress(jsonObject.getString("address"));
            }
            if (!jsonObject.isNull("job")) {
                userDTO.setJob(jsonObject.getString("job"));
            }
            if (!jsonObject.isNull("created_time")) {
                userDTO.setCreatedTime(jsonObject.getString("created_time"));
            }
            if (!jsonObject.isNull("edited_time")) {
                userDTO.setEditedTime(jsonObject.getString("edited_time"));
            }
            if (!jsonObject.isNull("avatar")) {
                userDTO.setAvatar(jsonObject.getString("avatar"));
            }
            if (!jsonObject.isNull("role")) {
                userDTO.setRole(jsonObject.getLong("role"));
            }
            if (!jsonObject.isNull("last_activity_time")) {
                userDTO.setLastActivityTime(jsonObject.getString("last_activity_time"));
            }
            if (!jsonObject.isNull("user_status")) {
                userDTO.setUserStatus(jsonObject.getLong("user_status"));
            }
        }
        return userDTO;
    }

    @Override
    public int updateUser(String userID, String name, String email, String phone, String dob, String gender, String address, String job) throws Exception {
        int i = 0;
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/updateUser")
                .header("content-type", "application/json")
                .body("{\n\"user_id\":\"" + userID + "\"," +
                        "\n\"name\":\"" + name + "\"," +
                        "\n\"email\": \"" + email + "\"," +
                        "\n\"phone_number\": \"" + phone + "\"," +
                        "\n\"dob\": \"" + dob + "\"," +
                        "\n\"gender\": \"" + gender + "\"," +
                        "\n\"address\": \"" + address + "\"," +
                        "\n\"job\": \"" + job + "\"\n}")
                .asJson();
        JSONObject jsonObject = response.getBody().getObject();
        i = jsonObject.getInt("status");
        return i;
    }

}
