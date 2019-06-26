from constants import Parameters, Mapping

SERVER = ""
HELP = [
    ["call admin", "register", "login", "get new courses", "get hot courses", "get top courses",
     "get categories", "get category courses", "get courses titles"],
    ["call admin", "logout", "profile", "request teaching", "create classroom",
     "create course", "join classroom", "enroll course", "rate course"]
]


def set_server(server):
    global SERVER
    SERVER = server


def get_func(name):
    if name == "request teaching":
        return request_teaching
    elif name == "approve request":
        return approve_request
    elif name == "register":
        return register
    elif name == "login":
        return login
    elif name == "logout":
        return logout
    elif name == "get new courses":
        return new_courses
    elif name == "get hot courses":
        return hot_courses
    elif name == "get top courses":
        return top_courses
    elif name == "get categories":
        return categories
    elif name == "get category courses":
        return category_courses
    elif name == "get courses titles":
        return courses_titles
    elif name == "enroll course":
        return enroll_course
    elif name == "join classroom":
        return join_classroom
    elif name == "get enrolled courses":
        return enrolled_courses
    elif name == "get joined classrooms":
        return joined_classrooms
    elif name == "profile":
        return profile
    elif name == "create course":
        return create_course
    elif name == "create classroom":
        return create_classroom
    elif name == "rate course":
        return rate_course


top_courses = {
    "mapping": Mapping.TOP_RATED_COURSES,
    "method": "get",
    "parameters": []
}

hot_courses = {
    "mapping": Mapping.HOT_COURSES,
    "method": "get",
    "parameters": []
}

new_courses = {
    "mapping": Mapping.NEW_COURSES,
    "method": "get",
    "parameters": []
}

categories = {
    "mapping": Mapping.CATEGORIES,
    "method": "get",
    "parameters": []
}

category_courses = {
    "mapping": Mapping.CATEGORY_COURSES,
    "method": "get",
    "parameters": []
}

courses_titles = {
    "mapping": Mapping.ALL_COURSES,
    "method": "get",
    "parameters": []
}

register = {
    "mapping": Mapping.REGISTER,
    "method": "post",
    "parameters": [
        Parameters.FIRST_NAME,
        Parameters.LAST_NAME,
        Parameters.EMAIL,
        Parameters.USERNAME,
        Parameters.PASSWORD,
        Parameters.GENDER,
        Parameters.DATE_OF_BIRTH
    ]
}


login = {
    "mapping": Mapping.LOGIN,
    "method": "get",
    "parameters": [
        Parameters.EMAIL,
        Parameters.USERNAME,
        Parameters.PASSWORD
    ]
}

profile = {
    "mapping": Mapping.PROFILE,
    "method": "get",
    "parameters": [
        Parameters.ACCESS_TOKEN
    ]
}

logout = {
    "mapping": Mapping.LOGOUT,
    "method": "get",
    "parameters": [
        Parameters.ACCESS_TOKEN
    ]
}


request_teaching = {
    "mapping": Mapping.REQUEST_TEACHING,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN
    ]
}

approve_request = {
    "mapping": Mapping.APPROVE_TEACHING_REQUEST,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN,
        Parameters.USER_ID
    ]
}

enroll_course = {
    "mapping": Mapping.ENROLL_COURSE,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN,
        Parameters.COURSE_ID
    ]
}

join_classroom = {
    "mapping": Mapping.JOIN_CLASSROOM,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN,
        Parameters.PASSCODE
    ]
}

enrolled_courses = {
    "mapping": Mapping.STUDENT_COURSES,
    "method": "get",
    "parameters": [
        Parameters.ACCESS_TOKEN
    ]
}

joined_classrooms = {
    "mapping": Mapping.STUDENT_CLASSROOMS,
    "method": "get",
    "parameters": [
        Parameters.ACCESS_TOKEN
    ]
}

create_course = {
    "mapping": Mapping.TEACHER_COURSES,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN,
        Parameters.Title,
        Parameters.Detailed_title,
        Parameters.Description,
        Parameters.CATEGORY,
        Parameters.Level
    ]
}

create_classroom = {
    "mapping": Mapping.TEACHER_CLASSROOMS,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN,
        Parameters.CLASSROOM_NAME
    ]
}

rate_course = {
    "mapping": Mapping.STUDENT_RATE_COURSE,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN,
        Parameters.COURSE_ID,
        Parameters.Rate
    ]
}

create_quiz = {
    "mapping": Mapping.TEACHER_QUIZ,
    "method": "post",
    "parameters": [
        Parameters.ACCESS_TOKEN,
        Parameters.SECTION_ID,
        Parameters.QUIZ_TITLE,
        Parameters.QUIZ_INSTRUCTIONS,
        Parameters.QUIZ_TIME
    ]
}