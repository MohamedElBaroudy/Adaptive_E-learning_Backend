package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.FancyModel.FancyQuestion;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.Model.*;
import com.adaptivelearning.server.Repository.*;
import com.adaptivelearning.server.Security.JwtTokenProvider;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    JwtTokenProvider jwtTokenChecker;



    @PostMapping(Mapping.TEACHER_QUIZ)
    public ResponseEntity<?> createQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
                                        @Valid @RequestParam(Param.SECTION_ID) Integer sectionId,
                                        @Valid @RequestParam(Param.QUIZ_TITLE) String title,
                                        @Valid @RequestParam(Param.QUIZ_INSTRUCTIONS) String instructions,
                                        @Valid @RequestParam(Param.QUIZ_TIME) Short time){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Section section = sectionRepository.findBySectionId(sectionId);

        if (section == null) {
            return new ResponseEntity<>("Section Is Not Present ",
                    HttpStatus.NOT_FOUND);
        }

        if (section.getCourse().getPublisher().getUserId()!=
                (user.getUserId())) {
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your section to add quiz in",
                    HttpStatus.FORBIDDEN);
        }
        Quiz quiz = new Quiz(title,instructions,time,(short)0);
        quiz.setSection(section);
        quizRepository.save(quiz);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(Mapping.TEACHER_QUIZ)
    public ResponseEntity<?> updateQuizInfo(@RequestParam(Param.ACCESS_TOKEN) String token,
                                            @Valid @RequestParam(Param.QUIZ_ID) Integer quizId,
                                            @Valid @RequestParam(value = Param.QUIZ_TITLE,required = false) String title,
                                            @Valid @RequestParam(value = Param.QUIZ_TIME,required = false) Short time,
                                            @Valid @RequestParam(value = Param.QUIZ_INSTRUCTIONS,required = false) String instructions){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Quiz quiz = quizRepository.findByQuizId(quizId);

        if (quiz == null)
            return new ResponseEntity<>("Not found quiz",HttpStatus.NOT_FOUND);

        if (quiz.getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your quiz to update",
                    HttpStatus.FORBIDDEN);

        if (title != null && !title.isEmpty())
            quiz.setTitle(title);
        if (instructions != null && !instructions.isEmpty())
            quiz.setInstructions(instructions);
        if (time != null){
            if(time < 5 || time > 60)
                return new ResponseEntity<>("time is less than 5 or more than 60",
                        HttpStatus.BAD_REQUEST);
            quiz.setTime(time);
        }

        quizRepository.save(quiz);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(Mapping.TEACHER_QUIZ)
    public ResponseEntity<?> deleteQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
                                        @Valid @RequestParam(Param.QUIZ_ID) Integer quizId){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Quiz quiz = quizRepository.findByQuizId(quizId);

        if (quiz == null)
            return new ResponseEntity<>("Not found quiz",HttpStatus.NOT_FOUND);

        if (quiz.getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your quiz to delete",
                    HttpStatus.FORBIDDEN);

        quizRepository.deleteById(quizId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(Mapping.QUIZ)
    public ResponseEntity<?> retrieveQuiz(@RequestParam(Param.ACCESS_TOKEN) String token,
                                          @Valid @RequestParam(Param.QUIZ_ID) Integer quizId){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Quiz quiz = quizRepository.findByQuizId(quizId);

        if (quiz == null)
            return new ResponseEntity<>("Not found quiz",HttpStatus.NOT_FOUND);

        if (quiz.getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()) && !quiz.getSection().getCourse().getLearners().contains(user))
            return new ResponseEntity<>("Not Allowed you are not the creator of this quiz or a student of this course",
                    HttpStatus.FORBIDDEN);

        FancyQuiz fancyQuiz = new FancyQuiz();
        return new ResponseEntity<>(fancyQuiz.toFancyQuizMapping(quiz),HttpStatus.OK);
    }

    @PostMapping(Mapping.QUIZ_QUESTION)
    public ResponseEntity<?> addQuestion(@RequestParam(Param.ACCESS_TOKEN) String token,
                                          @Valid @RequestParam(Param.QUIZ_ID) Integer quizId,
                                          @Valid @RequestParam(Param.QUESTION_BODY) String body,
                                          @Valid @RequestParam(Param.QUESTION_IS_MULTIPLE_CHOICE) Boolean isMultipleChoice,
                                          @Valid @RequestParam(Param.QUESTION_MARK) Short mark){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Quiz quiz = quizRepository.findByQuizId(quizId);

        if (quiz == null)
            return new ResponseEntity<>("Not found quiz",HttpStatus.NOT_FOUND);

        if (quiz.getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not the creator of this quiz",
                    HttpStatus.FORBIDDEN);

        Question question = new Question(body,isMultipleChoice,mark);
        question.setQuiz(quiz);
        questionRepository.save(question);
        quiz.setTotalMark((short) (quiz.getTotalMark()+mark));
        quizRepository.save(quiz);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(Mapping.QUESTION)
    public ResponseEntity<?> retrieveQuestion(@RequestParam(Param.ACCESS_TOKEN) String token,
                                              @Valid @RequestParam(Param.QUESTION_ID) Integer questionId){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Question question = questionRepository.findByQuestionId(questionId);

        if (question == null)
            return new ResponseEntity<>("Not found question",HttpStatus.NOT_FOUND);

        if (question.getQuiz().getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()) && !question.getQuiz().getSection().getCourse().getLearners().contains(user))
            return new ResponseEntity<>("Not Allowed you are not the creator of this quiz or a student of this course",
                    HttpStatus.FORBIDDEN);

        FancyQuestion fancyQuestion = new FancyQuestion();
        return new ResponseEntity<>(fancyQuestion.toFancyQuestionMapping(question),
                HttpStatus.OK);
    }

    @PutMapping(Mapping.QUIZ_QUESTION)
    public ResponseEntity<?> updateQuestion(@RequestParam(Param.ACCESS_TOKEN) String token,
                                              @Valid @RequestParam(Param.QUESTION_ID) Integer questionId,
                                            @Valid @RequestParam(value = Param.QUESTION_BODY,required = false) String body,
                                            @Valid @RequestParam(value = Param.QUESTION_IS_MULTIPLE_CHOICE,required = false) Boolean isMultipleChoice,
                                            @Valid @RequestParam(value = Param.QUESTION_MARK,required = false) Short mark){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Question question = questionRepository.findByQuestionId(questionId);

        if (question == null)
            return new ResponseEntity<>("Not found question",HttpStatus.NOT_FOUND);

        if (question.getQuiz().getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not the creator of this quiz to update it's content",
                    HttpStatus.FORBIDDEN);

        if(body != null && !body.isEmpty())
            question.setBody(body);
        if(isMultipleChoice != null)
            question.setMultipleChoice(isMultipleChoice);
        if (mark != null) {
            Quiz quiz = question.getQuiz();
            quiz.setTotalMark((short) (quiz.getTotalMark()-question.getMark()+mark));
            quizRepository.save(quiz);
            question.setMark(mark);
        }
        questionRepository.save(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(Mapping.QUIZ_QUESTION)
    public ResponseEntity<?> deleteQuestion(@RequestParam(Param.ACCESS_TOKEN) String token,
                                        @Valid @RequestParam(Param.QUESTION_ID) Integer questionId){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Question question = questionRepository.findByQuestionId(questionId);

        if (question == null)
            return new ResponseEntity<>("Not found question",HttpStatus.NOT_FOUND);

        if (question.getQuiz().getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your quiz to delete it's content",
                    HttpStatus.FORBIDDEN);

        Quiz quiz = question.getQuiz();
        quiz.setTotalMark((short) (quiz.getTotalMark()-question.getMark()));

        questionRepository.deleteById(questionId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(Mapping.QUeSTION_ANSWER)
    public ResponseEntity<?> addAnswer(@RequestParam(Param.ACCESS_TOKEN) String token,
                                       @Valid @RequestParam(Param.QUESTION_ID) Integer questionId,
                                       @Valid @RequestParam(Param.ANSWER_BODY) String body,
                                       @Valid @RequestParam(Param.ANSWER_IS_CORRECT) Boolean isCorrect){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Question question = questionRepository.findByQuestionId(questionId);

        if (question == null)
            return new ResponseEntity<>("Not found question",HttpStatus.NOT_FOUND);

        if (question.getQuiz().getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not the creator of this quiz",
                    HttpStatus.FORBIDDEN);

        if (isCorrect && !question.isMultipleChoice()){
            for (Answer existAnswer: question.getAnswers()){
                if (existAnswer.isCorrect())
                    return new ResponseEntity<>("Cannot have more than 1 correct answer update your question to multiple choice first",
                            HttpStatus.BAD_REQUEST);
            }
        }

        Answer answer = new Answer(body,isCorrect);
        answer.setQuestion(question);
        answerRepository.save(answer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(Mapping.QUeSTION_ANSWER)
    public ResponseEntity<?> updateAnswer(@RequestParam(Param.ACCESS_TOKEN) String token,
                                            @Valid @RequestParam(Param.ANSWER_ID) Integer answerId,
                                            @Valid @RequestParam(value = Param.ANSWER_BODY,required = false) String body,
                                            @Valid @RequestParam(value = Param.ANSWER_IS_CORRECT,required = false) Boolean isCorrect){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Answer answer = answerRepository.findByAnswerId(answerId);

        if (answer == null)
            return new ResponseEntity<>("Not found answer",HttpStatus.NOT_FOUND);

        if (answer.getQuestion().getQuiz().getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not the creator of this quiz to update it's content",
                    HttpStatus.FORBIDDEN);

        if (isCorrect != null && isCorrect && !answer.isCorrect() && !answer.getQuestion().isMultipleChoice()){
            for (Answer existAnswer: answer.getQuestion().getAnswers()){
                if (existAnswer.isCorrect())
                    return new ResponseEntity<>("Cannot have more than 1 correct answer update your question to multiple choice first or update the other correct answer body",
                            HttpStatus.BAD_REQUEST);
            }
        }

        boolean doesQuestionHasAnotherCorrectAnswer = false;

        if (answer.isCorrect() && isCorrect != null && !isCorrect){
            for (Answer existAnswer: answer.getQuestion().getAnswers()){
                if (existAnswer.isCorrect() && !existAnswer.equals(answer)) {
                    doesQuestionHasAnotherCorrectAnswer = true;
                    break;
                }
            }
        }
        else
            doesQuestionHasAnotherCorrectAnswer = true;

        if (!doesQuestionHasAnotherCorrectAnswer)
            return new ResponseEntity<>("Cannot make the only correct answer incorrect. change the body to be correct",
                    HttpStatus.BAD_REQUEST);

        if(body != null && !body.isEmpty())
            answer.setBody(body);
        if(isCorrect != null)
            answer.setCorrect(isCorrect);
        answerRepository.save(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(Mapping.QUeSTION_ANSWER)
    public ResponseEntity<?> deleteAnswer(@RequestParam(Param.ACCESS_TOKEN) String token,
                                            @Valid @RequestParam(Param.ANSWER_ID) Integer answerId){
        User user = userRepository.findByToken(token);

        if(user == null){
            return new ResponseEntity<>("User is not present",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!jwtTokenChecker.validateToken(token)) {
            return new ResponseEntity<>("Invalid token",
                    HttpStatus.UNAUTHORIZED);
        }

        Answer answer = answerRepository.findByAnswerId(answerId);

        if (answer == null)
            return new ResponseEntity<>("Not found question",HttpStatus.NOT_FOUND);

        if (answer.getQuestion().getQuiz().getSection().getCourse().getPublisher().getUserId()!=
                (user.getUserId()))
            return new ResponseEntity<>("Not Allowed you are not a teacher or this is not your quiz to delete it's content",
                    HttpStatus.FORBIDDEN);

        if (answer.isCorrect() && !answer.getQuestion().isMultipleChoice())
            return new ResponseEntity<>("Cannot remove the only correct answer. please add a correct answer first!",
                    HttpStatus.BAD_REQUEST);

        boolean doesQuestionHasAnotherCorrectAnswer = false;

        if (answer.isCorrect()){
            for (Answer existAnswer: answer.getQuestion().getAnswers()){
                if (existAnswer.isCorrect() && !existAnswer.equals(answer)) {
                    doesQuestionHasAnotherCorrectAnswer = true;
                    break;
                }
            }
        }
        else
            doesQuestionHasAnotherCorrectAnswer = true;

        if (!doesQuestionHasAnotherCorrectAnswer)
            return new ResponseEntity<>("Cannot remove the only correct answer. add another correct answer first",
                    HttpStatus.BAD_REQUEST);

        answerRepository.deleteById(answerId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
