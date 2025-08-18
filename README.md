

# API Testing with Postman — Boards & Lists (Localhost Demo)

This README documents end-to-end API testing of a simple Boards/Lists service using **Postman** against a local server.

---

## 1) Reflection & Summary

### Thought Process

Designed as a **mini lifecycle**: **Create → Use → Delete (Cleanup)**, covering happy paths (`201`, `200`) and cleanup verification. Used **environment variables** (`User_id`, `board_id`, `list-1`, `list_2`, `accessToken`) for repeatability across runs.

### Approach & Methodology

* **Environment-driven** setup (`{{BaseUrl}}`)
* **Postman Tests** validate status codes and payloads
* **Incremental verification** after each creation
* **Cleanup-first policy** for idempotent reruns (lists → board → user)

### Steps Taken

1. Create User → capture `User_id`, `accessToken`
2. Create Board → store `board_id`
3. Add Two Lists → store IDs
4. Delete One List
5. Delete Board
6. Delete User (with token)

### CRUD Operation Explanation

* **Create**: User, Board, Lists (`POST`)
* **Read**: Validate responses & optional `GET` for board details
* **Update**: Not used in this demo (env vars simulate state updates)
* **Delete**: Lists, Board, User (`DELETE`)

### Final Achievements

* Reproducible Postman collection with automated assertions
* Clear screenshots + examples for knowledge sharing
* Runner results confirm **all tests passed** ✅

---
## 2️⃣ Source of APIs

Before setting up the Postman environment, I verified the backend APIs directly in the **browser Developer Tools (Network tab)**.

The backend was running on:

```
http://localhost:3000/api
```

### ▶️ Observations from Network Tab

* **Signup API (`/signup`)**

  * Sent a `POST` request with email, password, and welcomeEmail flag.
  * **Payload captured:**

    ```json
    {
      "email": "sakif646@gmail.com",
      "password": "sakif123",
      "welcomeEmail": false
    }
    ```

  * 📷 Screenshot:  ![Payload](https://drive.google.com/uc?export=view&id=1F0fVRMByrv0vZZlnFakeC0yMlfk1f9bl)  

* **Response Preview**

  * On success, API returned a **JWT accessToken** and the newly created user.
  * Example response:

    ```json
    {
      "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "user": {
        "email": "sakif646@gmail.com",
        "welcomeEmail": false,
        "id": 11
      }
    }
    ```
  * 📷 Screenshot:  ![View Token Preview](https://drive.google.com/uc?export=view&id=1fN_Gt98mzsLTjTKfoPVAFhNEL0WIsZA7)  

* **Boards API (`/boards`)**

  * Once authenticated, I could create and fetch boards.
  * Example response (board created):

    ```json
    {
      "name": "Board-1",
      "user": 11,
      "starred": false,
      "created": "2025-08-14",
      "id": 11
    }
    ```

---


## 2) Environment Setup

**What this does:**
Defines variables for URLs and dynamic IDs used throughout the collection so requests are portable and easy to rerun.

| Variable      | Example Value               | Notes                     |
| ------------- | --------------------------- | ------------------------- |
| `BaseUrl`     | `http://localhost:3000/api` | Base API URL              |
| `board_id`    | `11`                        | Set after board creation  |
| `list_2`      | `26`                        | Set after creating list-2 |
| `list-1`      | `25`                        | Set after creating list-1 |
| `User_id`     | `31`                        | Set after user creation   |
| `accessToken` | `<jwt-token>`               | Set from signup response  |

📸 **Screenshot — Postman Environment Setup**  
![Environment Setup](https://drive.google.com/uc?export=view&id=1cbesq2kAH5loZRi3mv4ZoTrcMrmyaIMi)  

---

## 3) Create User

**What this does:**
Creates a new user. Stores `User_id` and `accessToken` in the environment for authenticated calls later.

**Request**

```http
POST {{BaseUrl}}/signup
```

**Body (JSON)**

```json
{
  "email": "sakif{{randomInt}}@gmail.com",
  "password": "sakif123",
  "welcomeEmail": false
}
```

**Tests**

```javascript
pm.test("Successful POST request", function () {
  pm.expect(pm.response.code).to.be.oneOf([201, 202]);
});

let response = pm.response.json();
let responseEmail = response.user.email;

pm.test("Email contains 'sakif' and ends with @gmail.com", function () {
  pm.expect(responseEmail).to.match(/sakif\d+@gmail\.com$/);
});

var jsonData = pm.response.json();
pm.environment.set("User_id", jsonData.user.id);
pm.environment.set("accessToken", jsonData.accessToken);
```

**Expected Response (201 Created)**

```json
{
  "accessToken": "<jwt-token>",
  "user": {
    "email": "sakif8249@gmail.com",
    "welcomeEmail": false,
    "id": 31
  }
}
```

📸 **Screenshots**

* Body/Response:  
  ![Create User – Body](https://drive.google.com/uc?export=view\&id=1Ybgl5Mq1Ad_l8aSUJnbo-Aekg5-uX4v3)  
* Tests:  
  ![Create User – Tests](https://drive.google.com/uc?export=view\&id=1uH-p22Xex2jPJOLCYermY9eCsjmpWVUA)  

---

## 4) Create Board

**What this does:**
Creates a board and saves its `id` to `board_id` for subsequent list operations and cleanup.

**Request**

```http
POST {{BaseUrl}}/boards
```

**Body (JSON)**

```json
{
  "name": "Automation Bord"
}
```

**Tests**

```javascript
pm.test("Status code is 201", function () {
  pm.response.to.have.status(201);
});

pm.test("Name: Automation Bord", function () {
  pm.expect(pm.response.json().name).to.eql("Automation Bord");
});

let response = pm.response.json();
pm.environment.set("board_id", response.id);
```

**Expected Response (201 Created)**

```json
{
  "name": "Automation Bord",
  "user": 0,
  "starred": false,
  "created": "2025-08-18",
  "id": 11
}
```

📸 **Screenshots**

* Request Body:  
  ![Create Board – Body](https://drive.google.com/uc?export=view\&id=1_gJwbngdiEIcZ3Rx5mGfMi2DkCXJnY6Z)  
* Tests & Response:  
  ![Create Board – Tests](https://drive.google.com/uc?export=view\&id=1Hyj_w3xcw2x2GyTv5vxRIHm8LAe91gEA)  

---

## 5) Add Two Lists

### List-1

**What this does:**
Creates **list-1** under the created board and stores its ID in `list-1`.

```http
POST {{BaseUrl}}/lists
```

**Body**

```json
{
  "boardId": {{board_id}},
  "name": "list-1",
  "order": 0
}
```

**Tests**

```javascript
pm.test("Status code is 201", function () {
  pm.response.to.have.status(201);
});

pm.test("Name: list-1", function () {
  var jsonData = pm.response.json();
  pm.expect(jsonData.name).to.eql("list-1");
});

let response = pm.response.json();
pm.environment.set("list-1", response.id);
```

**Expected Response**

```json
{
  "boardId": 11,
  "name": "list-1",
  "order": 0,
  "created": "2025-08-18",
  "id": 25
}
```

📸 **Screenshots**

* Add list-1 Body/Response:  
  ![Add List-1 Body](https://drive.google.com/uc?export=view\&id=1q3yOMHuIMUDtErLf69OWo1ASSW3iBbtS)  
* Add list-1 Tests:  
  ![Add List-1 Tests](https://drive.google.com/uc?export=view\&id=1Q3FqILfem7kg66Iq6rDAoAFu-gw4n5sW)  

---

### List-2

**What this does:**
Creates **list-2** under the same board and stores its ID in `list_2`. This is the one we’ll delete later.

```http
POST {{BaseUrl}}/lists
```

**Body**

```json
{
  "boardId": {{board_id}},
  "name": "list-2",
  "order": 0
}
```

**Tests**

```javascript
pm.test("Status code is 201", function () {
  pm.response.to.have.status(201);
});

var jsonData = pm.response.json();

pm.test("Board ID matches", function () {
  pm.expect(jsonData.boardId).to.eql(11);
});

pm.test("List name is: list-2", function () {
  pm.expect(jsonData.name).to.eql("list-2");
});

let response = pm.response.json();
pm.environment.set("list_2", response.id);
```

**Expected Response**

```json
{
  "boardId": 11,
  "name": "list-2",
  "order": 0,
  "created": "2025-08-18",
  "id": 26
}
```

📸 **Screenshots**

* Add list-2 Body/Response:  
  ![Add List-2 Body](https://drive.google.com/uc?export=view\&id=1tpAwJmIZ5VSxZIULSyu_H-n2eblCvYxI)  
* Add list-2 Tests:  
  ![Add List-2 Tests](https://drive.google.com/uc?export=view\&id=1ZQmQJUpRmEIjbzke5sed0FHxMVzoU98d)  

---

## 6) Delete One List

**What this does:**
Deletes **list-2** (stored in `list_2`) to validate deletion behavior and prep for board deletion.

**Request**

```http
DELETE {{BaseUrl}}/lists/{{list_2}}
```

**Expected Response (200 OK)**

```json
{}
```

📸 **Screenshot**
![Delete List](https://drive.google.com/uc?export=view\&id=1F5qe7ZYKkBIIauKSQBshQs8YXENUpjcH)  

---

## 7) Delete Board

**What this does:**
Deletes the board using `board_id` (after list cleanup) to keep the environment idempotent.

**Request**

```http
DELETE {{BaseUrl}}/boards/{{board_id}}
```

**Tests**

```javascript
pm.test("Board ID is Deleted", function () {
  pm.response.to.have.status(200);
});
```

**Expected Response**

```json
{}
```

📸 **Screenshot**  
![Delete Board](https://drive.google.com/uc?export=view\&id=19J8Q1Jpkc3DCV7wp8GbhrL_EdcBdIuFE)  

---

## 8) Delete User

**What this does:**
Deletes the user created in Step 3 using `User_id`. Requires `accessToken` as a Bearer token.

**Request**

```http
DELETE {{BaseUrl}}/users/{{User_id}}
```

**Authorization**

* Type: Bearer Token
* Token: `{{accessToken}}`

**Tests**

```javascript
pm.test(`User_id:${pm.environment.get("User_id")}, User successfully deleted`, function () {
  pm.response.to.have.status(200);
});
```

**Expected Response (200 OK)**

```json
{}
```

📸 **Screenshots**

* Authorization (Bearer token):  
  ![Delete User – Auth](https://drive.google.com/uc?export=view\&id=1PFIJHzmmLOiyupq7XgJqgnvpupZeBOnQ)  
* Tests (Validation):  
  ![Delete User – Test](https://drive.google.com/uc?export=view\&id=1ERuHvUgUWjmKJWkl56ob4AAFzGbkIQzz)  

---

## 9) Postman Collection Run Results

**What this shows:**
End-to-end execution with all assertions green — confirming the flow and cleanup are correct.

After running via **Postman Runner**:

* ✅ All 8 tests passed
* Avg response time: \~49ms

📸 **Screenshots**

* Runner Detailed:  
  ![Postman Runner Report](https://drive.google.com/uc?export=view\&id=1st5jHdtNQgNHYoR6qLCaFtQatsX5M6bh)  
* Runner Summary:  
  ![Postman Runner Summary](https://drive.google.com/uc?export=view\&id=1S6WUgCoqdcyau0CzRPoB6xazxtoX3ftG)  

---

# Final Notes

* The flow covers **Create → Read (validate) → Delete** across **User**, **Board**, and **Lists**.
* All dynamic IDs and tokens are saved to the environment for **repeatable runs**.


