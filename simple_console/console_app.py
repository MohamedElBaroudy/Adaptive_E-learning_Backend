from constants import Mapping, Parameters, Functions
from random import randrange
import requests
from time import sleep
from sys import exit


USERS = [["mohamedrambo" + str(randrange(0, 100)) + "@gmail.com", "mohamedX" + str(randrange(0, 100)), ""],
         ["tarekkabo" + str(randrange(0, 100)) + "@gmail.com", "tarekX" + str(randrange(0, 100)), ""],
         ["mahmoudbando" + str(randrange(0, 100)) + "@gmail.com", "mahmoudX" + str(randrange(0, 100)), ""],
         ["ahmedabdo" + str(randrange(0, 100)) + "@gmail.com", "ahmedX" + str(randrange(0, 100)), ""],
         ["hossam_snow" + str(randrange(0, 100)) + "@gmail.com", "hossamX" + str(randrange(0, 100)), ""],
         ["amir_sand" + str(randrange(0, 100)) + "@gmail.com", "amirX" + str(randrange(0, 100)), ""]
         ]
COURSES = ["CSE" + str(randrange(0, 1000)) + " Electrical Testing",
           "CSE" + str(randrange(0, 1000)) + " Data base",
           "CSE" + str(randrange(0, 1000)) + " Networks",
           "CSE" + str(randrange(0, 1000)) + " Neural Networks",
           "CSE" + str(randrange(0, 1000)) + " Security",
           "CSE" + str(randrange(0, 1000)) + " Control Systems",
           "CSE" + str(randrange(0, 1000)) + " Artificial Intelligence",
           "CSE" + str(randrange(0, 1000)) + " Distributed Systems",
           "CSE" + str(randrange(0, 1000)) + " Biomedical Engineering",
           "CSE" + str(randrange(0, 1000)) + " Machine Learning",
           "CSE" + str(randrange(0, 1000)) + " Big Data",
           "CSE" + str(randrange(0, 1000)) + " Image Processing"
           ]
CLASSROOMS = [["Physics" + " CSE" + str(randrange(0, 1000)) + " Classroom", ""],
              ["Maths" + " CSE" + str(randrange(0, 1000)) + " Classroom", ""],
              ["Chemistry" + " CSE" + str(randrange(0, 1000)) + " Classroom", ""],
              ["History" + " CSE" + str(randrange(0, 1000)) + " Classroom", ""],
              ["Geography" + " CSE" + str(randrange(0, 1000)) + " Classroom", ""],
              ["Literature" + " CSE" + str(randrange(0, 1000)) + " Classroom", ""]
              ]

EXIT_FLAG = 0
OPEN_FLAG = 1
ADMIN_FLAG = 0
GENERATED_FLAG = 0

USERID = 0
USERNAME = ""
USER_EMAIL = ""
PASSWORD = ""
TOKEN = ""
ADMIN_TOKEN = ""

SERVER = ""
SERVER_LINK = ""

EXEC = [USERNAME, SERVER]
PROMPT = "mode " + "(" + ", ".join(EXEC) + ")" + "> "
FUNCTION = ""


HELP = [
    ["call admin", "generate random", "generate random quizzes", "register", "login", "get new courses", "get hot courses", "get top courses",
     "get categories", "get category courses", "get courses titles"],
    ["call admin", "generate random", "generate random quizzes", "logout", "profile", "request teaching", "create classroom", "get enrolled courses",
     "get joined classrooms", "create course", "join classroom", "enroll course", "rate course"]
]


def update_prompt():
    global EXEC, PROMPT
    EXEC = [USERNAME, SERVER]
    if ADMIN_FLAG == 1:
        PROMPT = "mode " + "(" + ", ".join(EXEC) + ")" + "# "
    else:
        PROMPT = "mode " + "(" + ", ".join(EXEC) + ")" + "> "


def welcome_message():
    print("Welcome To Adaptive E-Learning Console. It's meant to control the local/web server with ease and comfort.")
    sleep(1)
    print("If you want to exit just write \"quit()\", \"quit\", \"exit()\" or \"exit\".")
    sleep(1)
    print("Make sure that at any time you seek help I got your back <3. just type \"help()\" or \"help\"")
    sleep(1)
    print("If you want admin please enter command \"call admin\" and you will be at privileged mode with \"#\".")
    sleep(1)
    print("Dola send his regards.")
    sleep(1)


def choose_server():
    server = input("Which server to work on? \"web\" or \"local\"? ")
    try:
        global SERVER
        SERVER = server.lower()
        if SERVER not in ["web", "local"]:
            raise Exception
        update_prompt()
        global SERVER_LINK
        SERVER_LINK = Mapping.SERVER[server]
    except Exception:
        print("one choice is available")


def show_help():
    if USERNAME == "":
        mode = "Not logged in mode"
        functions = HELP[0]
    else:
        mode = "Logged in mode"
        functions = HELP[1]
    print("You are in \"" + mode + "\" and work on \"" + SERVER + "\" server.")
    for i in functions:
        print(i)


def get_user_request():
    return input(PROMPT)


def exec_request(request_dictionary):
    global USERNAME,USER_EMAIL
    username = ""
    email = ""
    request = SERVER_LINK + request_dictionary["mapping"]
    method = request_dictionary["method"]
    if len(request_dictionary["parameters"]) > 0:
        request += "?"
        for x in request_dictionary["parameters"]:
            if x == "token":
                request += x + "=" + TOKEN + "&"
                continue
            user_input = input(x+" : ")
            request += x + "=" + user_input +"&"
            if x == "username":
                username = user_input
            if x == "email":
                email = user_input
        if method == "get":
            response = requests.get(request[:-1])
        elif method == "put":
            response = requests.put(request[:-1])
        elif method == "post":
            response = requests.post(request[:-1])
        elif method == "delete":
            response = requests.delete(request[:-1])
    else:
        if method == "get":
            response = requests.get(request)
        elif method == "put":
            response = requests.put(request)
        elif method == "post":
            response = requests.post(request)
        elif method == "delete":
            response = requests.delete(request)

    if 200 <= response.status_code < 300 and request_dictionary["mapping"] == Mapping.LOGIN:
        if username != "":
            USERNAME = username
        if email != "":
            USER_EMAIL = email
        update_prompt()

    return response


def main():
    global OPEN_FLAG, USERNAME, USERID, TOKEN, ADMIN_TOKEN, ADMIN_FLAG
    global USERS, COURSES, CLASSROOMS, ADMIN_TOKEN, GENERATED_FLAG
    while EXIT_FLAG != 1:
        if OPEN_FLAG == 1:
            welcome_message()
            while SERVER not in ["web", "local"]:
                choose_server()
            OPEN_FLAG = 0

        request = get_user_request()

        if request.lower() in ["quit()", "quit", "exit()", "exit"]:
            break
        if request.lower() in ["help", "help()"]:
            show_help()
            continue

        if request == "generate random":
            if ADMIN_FLAG != 1:
                print("cannot do this at normal mode please \"call admin\" first!")
                continue
            global USERS, COURSES, CLASSROOMS, ADMIN_TOKEN, GENERATED_FLAG
            try:
                number_of_users = 0
                for i in USERS:
                    my_func = Functions.get_func("register")
                    request_api = SERVER_LINK + my_func["mapping"] + "?"
                    request_api += my_func["parameters"][0] + "=" + i[1] + "&"  # first name
                    request_api += my_func["parameters"][1] + "=" + "adel" + "&"  # last name
                    request_api += my_func["parameters"][2] + "=" + i[0] + "&"  # email
                    request_api += my_func["parameters"][3] + "=" + i[1] + "&"  # username
                    request_api += my_func["parameters"][4] + "=" + "123456789" + "&"  # password
                    request_api += my_func["parameters"][5] + "=" + "1" + "&"  # gender
                    request_api += my_func["parameters"][6] + "=" + "1980-07-24"  # date of birth

                    response = requests.post(request_api)
                    if response.status_code != 200:
                        print("cannot register")
                        continue
                    print(i[1], "registered")
                    my_func = Functions.get_func("login")
                    request_api = SERVER_LINK + my_func["mapping"] + "?"
                    request_api += my_func["parameters"][1] + "=" + i[1] + "&"  # username
                    request_api += my_func["parameters"][2] + "=" + "123456789" # password
                    response = requests.get(request_api)
                    if response.status_code != 200:
                        print("cannot login")
                        continue
                    print(i[1], "logged in")
                    i[2] = response.text
                    my_func = Functions.get_func("request teaching")
                    request_api = SERVER_LINK + my_func["mapping"] + "?"
                    request_api += my_func["parameters"][0] + "=" + i[2]  # token
                    response = requests.post(request_api)
                    if response.status_code != 204:
                        print("cannot request teaching")
                        continue
                    print(i[1], "teaching request sent")
                    request_api = SERVER_LINK + "/admin/requests?token=" + ADMIN_TOKEN
                    response = requests.get(request_api).json()
                    claimer_id = ""
                    for k in response:
                        if k["claimerEmail"] == i[0]:
                            claimer_id = k["claimerId"]
                            break
                    request_api = SERVER_LINK + "/admin/approve_teaching?token=" + ADMIN_TOKEN + "&user_id=" + str(claimer_id)
                    response = requests.put(request_api)
                    if response.status_code != 204:
                        print("cannot approve request")
                        continue
                    print(i[1], "teaching request approved")

                    # create 2 courses
                    for k in COURSES[number_of_users*2:(number_of_users*2)+2]:
                        my_func = Functions.get_func("create course")
                        request_api = SERVER_LINK + my_func["mapping"] + "?"
                        request_api += my_func["parameters"][0] + "=" + i[2] + "&"  # token
                        request_api += my_func["parameters"][1] + "=" + k + "&"  # title
                        request_api += my_func["parameters"][2] + "=" + k + " in detail" + "&"  # detailed title
                        request_api += my_func["parameters"][3] + "=" + k + " in detail" + "&"  # description
                        request_api += my_func["parameters"][4] + "=" + "IT and Software" + "&"  # category
                        request_api += my_func["parameters"][5] + "=" + "1"  # level
                        response = requests.post(request_api)
                        if response.status_code != 201:
                            print("cannot create course")
                            continue
                        print(i[1], "created", k, "course")


                    # create classroom
                    my_func = Functions.get_func("create classroom")
                    request_api = SERVER_LINK + my_func["mapping"] + "?"
                    request_api += my_func["parameters"][0] + "=" + i[2] + "&"  # token
                    request_api += my_func["parameters"][1] + "=" + CLASSROOMS[number_of_users][0]  # classroom name
                    response = requests.post(request_api)
                    if response.status_code != 201:
                        print("cannot create classroom")
                        continue
                    print(print(i[1], "created", CLASSROOMS[number_of_users][0], "classroom"))
                    CLASSROOMS[number_of_users][1] = response.text

                    number_of_users += 1  # increment number of users
                GENERATED_FLAG = 1
            except:
                print("something wrong happened.")

            print("########### users ###########")
            for i in USERS:
                print(i)
            print("########### courses ###########")
            for i in COURSES:
                print(i)
            print("########### classrooms ###########")
            for i in CLASSROOMS:
                print(i)

        if request == "generate random quizzes":
            if GENERATED_FLAG != 1:
                print("cannot do this at normal mode please \"generate random\" first!")
                continue
            try:
                for i in range(6):
                    current_token = USERS[i][2]
                    create_section = Functions.create_section
                    create_quiz = Functions.create_quiz
                    add_question = Functions.add_question
                    add_answer = Functions.add_answer

                    for j in range(2):
                        for k in range(2):
                            course_id = 2 * i + j + 1
                            section_id = 4*i + 2*j + k + 1
                            quiz_id = section_id
                            request_api = SERVER_LINK + create_section["mapping"] + "?"
                            request_api += create_section["parameters"][0] + "=" + current_token + "&"  # token
                            request_api += create_section["parameters"][1] + "=" + str(course_id) + "&"
                            request_api += create_section["parameters"][2] + "=" + "section " + str(k) + " \"you can name your section\""
                            response = requests.post(request_api)
                            if response.status_code > 300:
                                print("cannot create section")
                                break
                            request_api = SERVER_LINK + create_quiz["mapping"] + "?"
                            request_api += create_quiz["parameters"][0] + "=" + current_token + "&"  # token
                            request_api += create_quiz["parameters"][1] + "=" + str(section_id) + "&"
                            request_api += create_quiz["parameters"][2] + "=" + "section " + str(k+1) + " quiz" + "&"
                            request_api += create_quiz["parameters"][3] + "=" + "section " + str(k+1) + " instruction" + "&"
                            request_api += create_quiz["parameters"][4] + "=" + str(randrange(10,21))
                            response = requests.post(request_api)
                            if response.status_code > 300:
                                print("cannot create quiz")
                                break
                            for l in range(15):
                                if l%2 == 0 :
                                    request_api = SERVER_LINK + add_question["mapping"] + "?"
                                    request_api += add_question["parameters"][0] + "=" + current_token + "&"  # token
                                    request_api += add_question["parameters"][1] + "=" + str(quiz_id) + "&"
                                    request_api += add_question["parameters"][2] + "=" + "question " + str(l + 1) + " of single choice test" + "&"
                                    request_api += add_question["parameters"][3] + "=" + "false" + "&"
                                    request_api += add_question["parameters"][4] + "=" + str(randrange(2, 5)) + "&"
                                    request_api += add_question["parameters"][5] + "=" + str(randrange(1, 4)) + "&"
                                    request_api += add_question["parameters"][6] + "=" + "you can write here a reference of this question to help students"
                                    response = requests.post(request_api)
                                    if response.status_code > 300:
                                        print("cannot create question")
                                        break
                                    question_id = 60*i + 30*j + 15*k + l + 1
                                    correct_answer = randrange(0, 4)
                                    for o in range(4):
                                        request_api = SERVER_LINK + add_answer["mapping"] + "?"
                                        request_api += add_answer["parameters"][0] + "=" + current_token + "&"  # token
                                        request_api += add_answer["parameters"][1] + "=" + str(question_id) + "&"
                                        if correct_answer == o:
                                            request_api += add_answer["parameters"][2] + "=" + "answer " + str(o) + " and is correct" + "&"
                                            request_api += add_answer["parameters"][3] + "=" + "true"
                                        else:
                                            request_api += add_answer["parameters"][2] + "=" + "answer " + str(o) + " and is wrong" + "&"
                                            request_api += add_answer["parameters"][3] + "=" + "false"
                                        response = requests.post(request_api)
                                        if response.status_code > 300:
                                            print("cannot create answer")
                                            break
                                else:
                                    request_api = SERVER_LINK + add_question["mapping"] + "?"
                                    request_api += add_question["parameters"][0] + "=" + current_token + "&"  # token
                                    request_api += add_question["parameters"][1] + "=" + str(quiz_id) + "&"
                                    request_api += add_question["parameters"][2] + "=" + "question " + str(
                                        l + 1) + " of multiple choice test" + "&"
                                    request_api += add_question["parameters"][3] + "=" + "true" + "&"
                                    request_api += add_question["parameters"][4] + "=" + str(randrange(2, 5)) + "&"
                                    request_api += add_question["parameters"][5] + "=" + str(randrange(1, 4)) + "&"
                                    request_api += add_question["parameters"][
                                                       6] + "=" + "you can write here a reference of this question to help students"
                                    response = requests.post(request_api)
                                    if response.status_code > 300:
                                        print("cannot create answer")
                                        break
                                    question_id = 60 * i + 30 * j + 15 * k + l + 1
                                    for o in range(4):
                                        correct_answer = randrange(0, 2)
                                        request_api = SERVER_LINK + add_answer["mapping"] + "?"
                                        request_api += add_answer["parameters"][0] + "=" + current_token + "&"  # token
                                        request_api += add_answer["parameters"][1] + "=" + str(question_id) + "&"
                                        if correct_answer == 1:
                                            request_api += add_answer["parameters"][2] + "=" + "answer " + str(o) + " and is correct" + "&"
                                            request_api += add_answer["parameters"][3] + "=" + "true"
                                        else:
                                            request_api += add_answer["parameters"][2] + "=" + "answer " + str(o) + " and is wrong" + "&"
                                            request_api += add_answer["parameters"][3] + "=" + "false"
                                        response = requests.post(request_api)
                                        if response.status_code > 300:
                                            print("cannot create answer")
                                            break
            except:
                print("something wrong happened.")

        if request == "call admin":
            request_api = SERVER_LINK + "/auth/login?email=admin@gmail.com&password=123456789123456789"
            try:
                response = requests.get(request_api)
            except:
                print("Connection Error")
                continue
            ADMIN_TOKEN = response.text
            # print(ADMIN_TOKEN)
            ADMIN_FLAG = 1

        if USERNAME == "" and request != "call admin":
            if request not in HELP[0]:
                print("request is not implemented or not applicable in this mode")
                continue

            if request == "register":
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success")
                else:
                    print("failed, ", response.text)

            elif request == "login":
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    TOKEN = response.text
                    print("success")
                else:
                    print("failed, ", response.text)
            else:
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success")
                    print(response.json())
                else:
                    print("failed, ", response.text)

        elif USERNAME != "" and request != "call admin":
            if request not in HELP[1]:
                print("request is not implemented or not applicable in this mode")
                continue
            if request == "logout":
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success")
                    USERNAME = ""
                else:
                    print("failed, ", response.text)

            elif request == "request teaching":
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 400:
                    print("request succeed and wait for admin to approve ...")
                    if ADMIN_FLAG != 1:
                        print("cannot do this at normal mode please \"call admin\" first!")
                        continue
                    request_api = SERVER_LINK + "/admin/requests?token="+ADMIN_TOKEN
                    try:
                        response = requests.get(request_api).json()
                    except:
                        print("Connection Error")
                        continue
                    claimer_id = ""
                    for i in response:
                        if i["claimerEmail"] == USER_EMAIL:
                            claimer_id = i["claimerId"]
                            break
                    request_api = SERVER_LINK + "/admin/approve_teaching?token=" + ADMIN_TOKEN + "&user_id=" + str(claimer_id)
                    try:
                        response = requests.put(request_api)
                    except:
                        print("Connection Error")
                        continue
                    if 200 <= response.status_code < 300:
                        print("request approved")
                    else:
                        print("failed, ", response.text)
                else:
                    print("failed, ", response.text)

            elif request in ["enroll course", "rate course"]:
                if request == "enroll course":
                    wanted_course = input("Which course you want to enroll ? ")
                else:
                    wanted_course = input("Which course you want to rate ? ")
                my_func = Functions.get_func("get courses titles")
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success and your course is in this list : ")
                    minimized_courses_list = []
                    for i in response.json():
                        if i["title"] == wanted_course:
                            minimized_courses_list.append(i)
                    print(minimized_courses_list)
                else:
                    print("failed, ", response.text)
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success")
                else:
                    print("failed, ", response.text)

            elif request in ["get my courses", "get my classrooms", "profile"]:
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success")
                    print(response.json())
                else:
                    print("failed, ", response.text)
            elif request == "create classroom":
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success")
                    print(response.text)
                else:
                    print("failed, ", response.text)
            else:
                my_func = Functions.get_func(request)
                try:
                    response = exec_request(my_func)
                except:
                    print("Connection Error")
                    continue
                if 200 <= response.status_code < 300:
                    print("success")
                else:
                    print("failed, ", response.text)

        update_prompt()


    sleep(1)
    exit()


if __name__ == '__main__':
    main()
