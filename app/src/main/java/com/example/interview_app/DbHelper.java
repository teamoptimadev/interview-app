package com.example.interview_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;


    private Context getContextFromDatabase(SQLiteDatabase db) {
        return context;
    }

    private static final String DB_NAME = "interview_app.db";
    private static final int DB_VERSION = 5;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "full_name TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "interviews_attended INTEGER DEFAULT 0, " +
                "accuracy REAL DEFAULT 0.0, " +
                "performance TEXT)");

        db.execSQL("CREATE TABLE interviews (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT)");

        db.execSQL("INSERT INTO interviews (title, description) VALUES " +
                "('Frontend', 'Modern UI development using React and Tailwind CSS for responsive and accessible web interfaces.')," +
                "('Backend', 'Server-side application development using Java Spring Boot with RESTful APIs and database integration.')," +
                "('Full Stack', 'Building scalable web applications integrating React frontend with Node.js and Express backend services.')," +
                "('Android Development', 'Developing native Android apps with Java and XML layouts, integrating APIs and local databases.')," +
                "('React', 'Developing interactive and modular React components with efficient state management using hooks and context.')," +
                "('Next.js', 'Creating optimized, SEO-friendly web applications with server-side rendering and API routes using Next.js.')," +
                "('AIML', 'Implementing machine learning models for predictive analytics, natural language processing, and computer vision tasks.')," +
                "('Operating System', 'Understanding process management, scheduling, memory allocation, and system calls in OS design.')," +
                "('Computer Networks', 'Studying network layers, protocols (TCP/IP, HTTP, DNS), and data transmission principles.')," +
                "('Computer Architecture', 'Analyzing CPU organization, instruction cycles, pipelining, and memory hierarchy in modern processors.')," +
                "('Data Structures and Algorithms', 'Solving computational problems using efficient data structures like trees, heaps, and graphs.')," +
                "('Software Development', 'Applying software engineering principles — version control, testing, and agile workflows for production systems.')");

        db.execSQL("CREATE TABLE questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "interview_id INTEGER NOT NULL, " +
                "question_type TEXT CHECK(question_type IN ('MCQ','TrueFalse','OneWord')), " +
                "question_text TEXT NOT NULL, " +
                "option1 TEXT, " +
                "option2 TEXT, " +
                "option3 TEXT, " +
                "option4 TEXT, " +
                "answer TEXT, " +
                "FOREIGN KEY(interview_id) REFERENCES interviews(id) ON DELETE CASCADE)");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(1, 'MCQ', 'Which HTML tag is used to include JavaScript code?', '<script>', '<js>', '<javascript>', '<code>', '<script>'),\n" +
                "(1, 'MCQ', 'Which CSS property controls text size?', 'font-style', 'font-size', 'text-size', 'text-style', 'font-size'),\n" +
                "(1, 'TrueFalse', 'Flexbox is used for creating responsive layouts. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(1, 'OneWord', 'HTML stands for _______.', NULL, NULL, NULL, NULL, 'HyperText Markup Language'),\n" +
                "(1, 'OneWord', 'CSS stands for _______.', NULL, NULL, NULL, NULL, 'Cascading Style Sheets');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(2, 'MCQ', 'Which protocol is commonly used for REST APIs?', 'HTTP', 'FTP', 'SMTP', 'SSH', 'HTTP'),\n" +
                "(2, 'MCQ', 'Which database is relational?', 'MongoDB', 'Firebase', 'PostgreSQL', 'Redis', 'PostgreSQL'),\n" +
                "(2, 'TrueFalse', 'Spring Boot simplifies Java backend development. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(2, 'OneWord', 'The language most commonly used with Spring Boot is _______.', NULL, NULL, NULL, NULL, 'Java'),\n" +
                "(2, 'OneWord', 'An API endpoint uses the _______ protocol.', NULL, NULL, NULL, NULL, 'HTTP');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(3, 'MCQ', 'Which technology is typically used for frontend in a full stack application?', 'React', 'Node.js', 'MongoDB', 'Express', 'React'),\n" +
                "(3, 'MCQ', 'Which of the following best describes MERN stack?', 'MongoDB, Express, React, Node', 'MySQL, Express, React, Nest', 'MongoDB, Electron, Redux, Node', 'MongoDB, Express, React, Next', 'MongoDB, Express, React, Node'),\n" +
                "(3, 'TrueFalse', 'Full Stack developers work on both client and server sides. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(3, 'OneWord', 'The database used in the MERN stack is _______.', NULL, NULL, NULL, NULL, 'MongoDB'),\n" +
                "(3, 'OneWord', 'The backend in MERN stack is handled by _______.', NULL, NULL, NULL, NULL, 'Node.js');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(4, 'MCQ', 'Which language is officially supported for Android development?', 'C++', 'Python', 'Kotlin', 'Swift', 'Kotlin'),\n" +
                "(4, 'MCQ', 'Which file declares permissions in an Android app?', 'manifest.xml', 'MainActivity.java', 'gradle.build', 'app.config', 'manifest.xml'),\n" +
                "(4, 'TrueFalse', 'Android apps can only be developed using Java. (True/False)', NULL, NULL, NULL, NULL, 'False'),\n" +
                "(4, 'OneWord', 'The main thread in Android is called the _______ thread.', NULL, NULL, NULL, NULL, 'UI'),\n" +
                "(4, 'OneWord', 'APK stands for _______.', NULL, NULL, NULL, NULL, 'Android Package');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(5, 'MCQ', 'What is used to manage component state in React?', 'setState()', 'this.state()', 'useState()', 'context()', 'useState()'),\n" +
                "(5, 'MCQ', 'Which of the following is not a React hook?', 'useEffect', 'useFetch', 'useState', 'useContext', 'useFetch'),\n" +
                "(5, 'TrueFalse', 'JSX allows writing HTML directly within JavaScript. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(5, 'OneWord', 'React was developed by _______.', NULL, NULL, NULL, NULL, 'Facebook'),\n" +
                "(5, 'OneWord', 'React uses a _______ DOM for efficient updates.', NULL, NULL, NULL, NULL, 'Virtual');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(6, 'MCQ', 'Which rendering method does Next.js use by default?', 'Client-side Rendering', 'Server-side Rendering', 'Static Rendering', 'Hydration Rendering', 'Server-side Rendering'),\n" +
                "(6, 'MCQ', 'What is the default directory for pages in a Next.js project?', '/src', '/pages', '/routes', '/app', '/pages'),\n" +
                "(6, 'TrueFalse', 'Next.js supports both static and dynamic routing. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(6, 'OneWord', 'Next.js is built on top of _______.', NULL, NULL, NULL, NULL, 'React'),\n" +
                "(6, 'OneWord', 'API routes in Next.js are created inside the _______ folder.', NULL, NULL, NULL, NULL, 'pages/api');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(7, 'MCQ', 'Which algorithm is used for classification?', 'K-Means', 'KNN', 'PCA', 'Apriori', 'KNN'),\n" +
                "(7, 'MCQ', 'Which library is used for machine learning in Python?', 'React', 'NumPy', 'TensorFlow', 'Pandas', 'TensorFlow'),\n" +
                "(7, 'TrueFalse', 'Supervised learning requires labeled data. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(7, 'OneWord', 'AI stands for _______.', NULL, NULL, NULL, NULL, 'Artificial Intelligence'),\n" +
                "(7, 'OneWord', 'ML stands for _______.', NULL, NULL, NULL, NULL, 'Machine Learning');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(8, 'MCQ', 'Which of the following is not an OS?', 'Windows', 'Linux', 'Oracle', 'macOS', 'Oracle'),\n" +
                "(8, 'MCQ', 'What is used for process scheduling?', 'Compiler', 'Scheduler', 'Interpreter', 'Dispatcher', 'Scheduler'),\n" +
                "(8, 'TrueFalse', 'Paging divides memory into fixed-size blocks. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(8, 'OneWord', 'The kernel is part of the _______.', NULL, NULL, NULL, NULL, 'Operating System'),\n" +
                "(8, 'OneWord', 'CPU scheduling algorithm that gives priority to shortest job is _______.', NULL, NULL, NULL, NULL, 'SJF');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(9, 'MCQ', 'Which protocol is used to transfer web pages?', 'HTTP', 'SMTP', 'FTP', 'SNMP', 'HTTP'),\n" +
                "(9, 'MCQ', 'Which layer of the OSI model handles routing?', 'Transport', 'Network', 'Session', 'Application', 'Network'),\n" +
                "(9, 'TrueFalse', 'TCP is a connection-oriented protocol. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(9, 'OneWord', 'IP stands for _______.', NULL, NULL, NULL, NULL, 'Internet Protocol'),\n" +
                "(9, 'OneWord', 'DNS resolves _______ names to IP addresses.', NULL, NULL, NULL, NULL, 'Domain');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(10, 'MCQ', 'Which component performs arithmetic and logic operations?', 'CU', 'ALU', 'Memory Unit', 'Register', 'ALU'),\n" +
                "(10, 'MCQ', 'Which type of memory is volatile?', 'ROM', 'Flash', 'RAM', 'SSD', 'RAM'),\n" +
                "(10, 'TrueFalse', 'Pipelining improves CPU throughput. (True/False)', NULL, NULL, NULL, NULL, 'True'),\n" +
                "(10, 'OneWord', 'CPU stands for _______.', NULL, NULL, NULL, NULL, 'Central Processing Unit'),\n" +
                "(10, 'OneWord', 'The control unit manages the _______ flow within CPU.', NULL, NULL, NULL, NULL, 'Instruction');\n");

        db.execSQL("INSERT INTO questions (interview_id, question_type, question_text, option1, option2, option3, option4, answer) VALUES\n" +
                "(11, 'MCQ', 'Which data structure works on FIFO?', 'Stack', 'Queue', 'Tree', 'Graph', 'Queue'),\n" +
                "(11, 'MCQ', 'Which sorting algorithm has O(n log n) average complexity?', 'Bubble Sort', 'Insertion Sort', 'Quick Sort', 'Selection Sort', 'Quick Sort'),\n" +
                "(11, 'TrueFalse', 'A binary tree can have more than two children. (True/False)', NULL, NULL, NULL, NULL, 'False'),\n" +
                "(11, 'OneWord', 'DFS stands for _______.', NULL, NULL, NULL, NULL, 'Depth First Search'),\n" +
                "(11, 'OneWord', 'In a graph, edges connect _______.', NULL, NULL, NULL, NULL, 'Vertices');\n");

        db.execSQL("CREATE TABLE user_interviews (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "interview_id INTEGER NOT NULL, " +
                "score REAL, " +
                "performance TEXT, " +
                "attended_on DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "FOREIGN KEY(interview_id) REFERENCES interviews(id) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_interviews");
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS interviews");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }



    public long registerUser(String fullName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", fullName);
        values.put("email", email);
        values.put("password", password);

        long userId = db.insert("users", null, values);

        if (userId != -1) {
            Context context = getContextFromDatabase(db);
            if (context != null) {
                android.content.SharedPreferences prefs =
                        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                android.content.SharedPreferences.Editor editor = prefs.edit();
                editor.putLong("user_id", userId);
                editor.putString("full_name", fullName);
                editor.apply();
            }
        }

        return userId;
    }




    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public List<CardItem> getAllInterviews() {
        List<CardItem> interviewList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM interviews", null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                interviewList.add(new CardItem(title, description));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return interviewList;
    }

    public List<CardItem> getAllHomeInterviews(int userId) {
        List<CardItem> interviewList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT i.title, i.description " +
                "FROM user_interviews ui " +
                "JOIN interviews i ON ui.interview_id = i.id " +
                "WHERE ui.user_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                interviewList.add(new CardItem(title, description));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return interviewList;
    }


    public boolean addUserInterviewIfNotExists(int userId, int interviewId, double score, String performance) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user_interviews WHERE user_id = ? AND interview_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(interviewId)});

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        if (exists) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("interview_id", interviewId);
        values.put("score", score);
        values.put("performance", performance);

        long result = db.insert("user_interviews", null, values);
        return result != -1;
    }

    public int getInterviewIdByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM interviews WHERE title = ?", new String[]{title});

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }

        cursor.close();
        return id;
    }


    public boolean deleteUserInterview(int userId, int interviewId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(
                "user_interviews",
                "user_id = ? AND interview_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(interviewId)}
        );
        return rowsDeleted > 0;
    }


    public List<Question> getQuestionsByInterviewId(int interviewId) {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT question_type, question_text, option1, option2, option3, option4, answer " +
                        "FROM questions WHERE interview_id = ?",
                new String[]{String.valueOf(interviewId)}
        );

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(cursor.getColumnIndexOrThrow("question_type"));
                String text = cursor.getString(cursor.getColumnIndexOrThrow("question_text"));
                String opt1 = cursor.getString(cursor.getColumnIndexOrThrow("option1"));
                String opt2 = cursor.getString(cursor.getColumnIndexOrThrow("option2"));
                String opt3 = cursor.getString(cursor.getColumnIndexOrThrow("option3"));
                String opt4 = cursor.getString(cursor.getColumnIndexOrThrow("option4"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                questions.add(new Question(type, text, opt1, opt2, opt3, opt4, answer));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questions;
    }


    public void updateUserInterviewScore(int userId, int interviewId, double scorePercent, String performance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", scorePercent);
        values.put("performance", performance);

        int rows = db.update("user_interviews", values, "user_id=? AND interview_id=?",
                new String[]{String.valueOf(userId), String.valueOf(interviewId)});

        if (rows == 0) {
            values.put("user_id", userId);
            values.put("interview_id", interviewId);
            db.insert("user_interviews", null, values);
        }

        db.execSQL("UPDATE users SET " +
                        "interviews_attended = interviews_attended + 1, " +
                        "accuracy = ?, " +
                        "performance = ? " +
                        "WHERE id = ?",
                new Object[]{scorePercent, performance, userId});
    }




    public UserStats getUserStats(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT interviews_attended, accuracy, performance FROM users WHERE id = ?",
                new String[]{String.valueOf(userId)}
        );

        UserStats stats = null;

        if (cursor.moveToFirst()) {
            int interviewsAttended = cursor.getInt(cursor.getColumnIndexOrThrow("interviews_attended"));
            float accuracy = cursor.getFloat(cursor.getColumnIndexOrThrow("accuracy"));
            String performance = cursor.getString(cursor.getColumnIndexOrThrow("performance"));
            stats = new UserStats(interviewsAttended, accuracy, performance);
        }

        cursor.close();
        db.close();
        return stats;
    }





}
