import sqlite3
import tkinter as tk
from tkinter import messagebox, ttk, scrolledtext, filedialog
from datetime import datetime
import re
import csv
from io import StringIO

# =====================================================================
# OFFLINE SCHOOL MANAGEMENT SYSTEM
# =====================================================================

class SchoolManagementSystem:
    def __init__(self, root):
        self.root = root
        self.root.title("School Management System")
        self.root.geometry("1000x750")
        self.root.configure(bg="#f0f0f0")
        
        # Initialize database
        self.db_init()
        
        # Current user
        self.current_user = None
        self.current_user_role = None
        
        # Show login/signup screen
        self.show_login_screen()
    
    def db_init(self):
        """Initialize SQLite database with required tables"""
        try:
            self.conn = sqlite3.connect('school_management.db')
            self.cursor = self.conn.cursor()
            
            # Users table
            self.cursor.execute('''
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    role TEXT NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            ''')
            
            # Teachers table
            self.cursor.execute('''
                CREATE TABLE IF NOT EXISTS teachers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    subject TEXT,
                    teacher_id TEXT UNIQUE,
                    FOREIGN KEY(user_id) REFERENCES users(id)
                )
            ''')
            
            # Students table
            self.cursor.execute('''
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER,
                    name TEXT NOT NULL,
                    email TEXT,
                    form_class TEXT,
                    student_id TEXT UNIQUE,
                    date_enrolled TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY(user_id) REFERENCES users(id)
                )
            ''')
            
            # Student Marks/Grades table
            self.cursor.execute('''
                CREATE TABLE IF NOT EXISTS student_marks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id INTEGER NOT NULL,
                    subject TEXT NOT NULL,
                    term INTEGER,
                    mark_1 REAL,
                    mark_2 REAL,
                    mark_3 REAL,
                    mark_4 REAL,
                    exam_mark REAL,
                    total_mark REAL,
                    grade TEXT,
                    date_recorded TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY(student_id) REFERENCES students(id)
                )
            ''')
            
            # Timetable table
            self.cursor.execute('''
                CREATE TABLE IF NOT EXISTS timetable (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    day TEXT,
                    form_class TEXT,
                    time_slot TEXT,
                    room_number TEXT
                )
            ''')
            
            # Roster/Duty table
            self.cursor.execute('''
                CREATE TABLE IF NOT EXISTS roster (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    week_number INTEGER,
                    date TEXT,
                    teachers TEXT,
                    classes TEXT
                )
            ''')
            
            self.conn.commit()
        except sqlite3.Error as e:
            messagebox.showerror("Database Error", f"Error initializing database: {e}")
    
    def validate_email(self, email):
        """Validate email format"""
        pattern = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
        return re.match(pattern, email) is not None
    
    def show_login_screen(self):
        """Display login/signup screen"""
        self.clear_screen()
        
        # Main frame
        main_frame = ttk.Frame(self.root)
        main_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=20)
        
        # Title
        title_label = ttk.Label(main_frame, text="School Management System", 
                               font=("Arial", 20, "bold"))
        title_label.pack(pady=20)
        
        # Subtitle
        subtitle_label = ttk.Label(main_frame, text="Login or Create Account",
                                  font=("Arial", 12))
        subtitle_label.pack(pady=10)
        
        # Tab control for login/signup
        self.notebook = ttk.Notebook(main_frame)
        self.notebook.pack(fill=tk.BOTH, expand=True, pady=20)
        
        # Login tab
        login_frame = ttk.Frame(self.notebook)
        self.notebook.add(login_frame, text="Login")
        self.create_login_form(login_frame)
        
        # Signup tab
        signup_frame = ttk.Frame(self.notebook)
        self.notebook.add(signup_frame, text="Sign Up")
        self.create_signup_form(signup_frame)
    
    def create_login_form(self, parent):
        """Create login form"""
        form_frame = ttk.Frame(parent)
        form_frame.pack(fill=tk.BOTH, expand=True, padx=30, pady=30)
        
        # Email
        ttk.Label(form_frame, text="Email:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        self.login_email = ttk.Entry(form_frame, width=40, font=("Arial", 10))
        self.login_email.pack(fill=tk.X, pady=5)
        
        # Password
        ttk.Label(form_frame, text="Password:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        self.login_password = ttk.Entry(form_frame, width=40, font=("Arial", 10), show="*")
        self.login_password.pack(fill=tk.X, pady=5)
        
        # Login button
        ttk.Button(form_frame, text="Login", command=self.login_user).pack(pady=20, fill=tk.X)
    
    def create_signup_form(self, parent):
        """Create signup form with name and email"""
        form_frame = ttk.Frame(parent)
        form_frame.pack(fill=tk.BOTH, expand=True, padx=30, pady=30)
        
        # Full Name
        ttk.Label(form_frame, text="Full Name:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        self.signup_name = ttk.Entry(form_frame, width=40, font=("Arial", 10))
        self.signup_name.pack(fill=tk.X, pady=5)
        
        # Email
        ttk.Label(form_frame, text="Email:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        self.signup_email = ttk.Entry(form_frame, width=40, font=("Arial", 10))
        self.signup_email.pack(fill=tk.X, pady=5)
        
        # Password
        ttk.Label(form_frame, text="Password:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        self.signup_password = ttk.Entry(form_frame, width=40, font=("Arial", 10), show="*")
        self.signup_password.pack(fill=tk.X, pady=5)
        
        # Confirm Password
        ttk.Label(form_frame, text="Confirm Password:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        self.signup_confirm_password = ttk.Entry(form_frame, width=40, font=("Arial", 10), show="*")
        self.signup_confirm_password.pack(fill=tk.X, pady=5)
        
        # Role
        ttk.Label(form_frame, text="Role:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        self.signup_role = ttk.Combobox(form_frame, values=["Teacher", "Student", "Admin"], 
                                       state="readonly", width=37, font=("Arial", 10))
        self.signup_role.pack(fill=tk.X, pady=5)
        
        # Signup button
        ttk.Button(form_frame, text="Sign Up", command=self.signup_user).pack(pady=20, fill=tk.X)
    
    def signup_user(self):
        """Handle user signup"""
        name = self.signup_name.get().strip()
        email = self.signup_email.get().strip()
        password = self.signup_password.get()
        confirm_password = self.signup_confirm_password.get()
        role = self.signup_role.get()
        
        # Validation
        if not name:
            messagebox.showerror("Signup Error", "Please enter your full name")
            return
        
        if not email:
            messagebox.showerror("Signup Error", "Please enter your email")
            return
        
        if not self.validate_email(email):
            messagebox.showerror("Signup Error", "Please enter a valid email address")
            return
        
        if not password:
            messagebox.showerror("Signup Error", "Please enter a password")
            return
        
        if len(password) < 6:
            messagebox.showerror("Signup Error", "Password must be at least 6 characters")
            return
        
        if password != confirm_password:
            messagebox.showerror("Signup Error", "Passwords do not match")
            return
        
        if not role:
            messagebox.showerror("Signup Error", "Please select a role")
            return
        
        # Check if email already exists
        try:
            self.cursor.execute("SELECT id FROM users WHERE email = ?", (email,))
            if self.cursor.fetchone():
                messagebox.showerror("Signup Error", "Email already registered")
                return
            
            # Insert new user
            self.cursor.execute(
                "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)",
                (name, email, password, role)
            )
            self.conn.commit()
            
            messagebox.showinfo("Success", f"Account created successfully!\nWelcome {name}")
            
            # Clear form
            self.signup_name.delete(0, tk.END)
            self.signup_email.delete(0, tk.END)
            self.signup_password.delete(0, tk.END)
            self.signup_confirm_password.delete(0, tk.END)
            self.signup_role.set("")
            
            # Switch to login tab
            self.notebook.select(0)
            
        except sqlite3.Error as e:
            messagebox.showerror("Database Error", f"Error creating account: {e}")
    
    def login_user(self):
        """Handle user login"""
        email = self.login_email.get().strip()
        password = self.login_password.get()
        
        if not email or not password:
            messagebox.showerror("Login Error", "Please enter email and password")
            return
        
        try:
            self.cursor.execute(
                "SELECT id, name, role FROM users WHERE email = ? AND password = ?",
                (email, password)
            )
            user = self.cursor.fetchone()
            
            if user:
                self.current_user = user[0]
                self.current_user_name = user[1]
                self.current_user_role = user[2]
                self.show_dashboard()
            else:
                messagebox.showerror("Login Error", "Invalid email or password")
        
        except sqlite3.Error as e:
            messagebox.showerror("Database Error", f"Error during login: {e}")
    
    def show_dashboard(self):
        """Show main dashboard after login"""
        self.clear_screen()
        
        # Header frame
        header_frame = ttk.Frame(self.root)
        header_frame.pack(fill=tk.X, padx=20, pady=10)
        
        ttk.Label(header_frame, text=f"Welcome, {self.current_user_name} ({self.current_user_role})", 
                 font=("Arial", 14, "bold")).pack(side=tk.LEFT)
        
        ttk.Button(header_frame, text="Logout", command=self.logout).pack(side=tk.RIGHT)
        
        # Menu frame
        menu_frame = ttk.Frame(self.root)
        menu_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=20)
        
        ttk.Label(menu_frame, text="Dashboard", font=("Arial", 12, "bold")).pack(pady=10)
        
        # Buttons based on role
        if self.current_user_role == "Admin":
            ttk.Button(menu_frame, text="Manage Timetable", width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="Manage Roster", width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="Bulk Register Students", command=self.show_bulk_student_registration, width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="View All Students", command=self.show_all_students, width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="Student Marks Management", command=self.show_marks_management, width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="Manage Users", width=50).pack(pady=5, fill=tk.X)
        
        elif self.current_user_role == "Teacher":
            ttk.Button(menu_frame, text="View My Timetable", width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="View Roster", width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="Register Student Marks", command=self.show_marks_management, width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="View Student List", command=self.show_all_students, width=50).pack(pady=5, fill=tk.X)
        
        elif self.current_user_role == "Student":
            ttk.Button(menu_frame, text="View My Classes", width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="View Timetable", width=50).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="View My Marks", command=self.show_my_marks, width=50).pack(pady=5, fill=tk.X)
    
    def show_bulk_student_registration(self):
        """Show bulk student registration interface"""
        self.clear_screen()
        
        # Header
        header_frame = ttk.Frame(self.root)
        header_frame.pack(fill=tk.X, padx=20, pady=10)
        ttk.Label(header_frame, text="Bulk Student Registration", font=("Arial", 14, "bold")).pack(side=tk.LEFT)
        ttk.Button(header_frame, text="Back", command=self.show_dashboard).pack(side=tk.RIGHT)
        
        # Main content
        content_frame = ttk.Frame(self.root)
        content_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=20)
        
        # Instructions
        ttk.Label(content_frame, text="Paste student data in CSV format (Name, Email, Form Class, Student ID):", 
                 font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        
        ttk.Label(content_frame, text="Example:\nJohn Doe,john.doe@school.com,1 Red,STU001\nJane Smith,jane.smith@school.com,1 Yellow,STU002", 
                 font=("Arial", 9), foreground="gray").pack(anchor=tk.W, pady=5)
        
        # Text area for pasting data
        ttk.Label(content_frame, text="Paste Student Data:", font=("Arial", 10)).pack(anchor=tk.W, pady=(10, 5))
        self.bulk_data_text = scrolledtext.ScrolledText(content_frame, height=15, width=80, font=("Courier", 9))
        self.bulk_data_text.pack(fill=tk.BOTH, expand=True, pady=5)
        
        # Buttons frame
        button_frame = ttk.Frame(content_frame)
        button_frame.pack(fill=tk.X, pady=10)
        
        ttk.Button(button_frame, text="Import from CSV File", command=self.import_csv_file).pack(side=tk.LEFT, padx=5)
        ttk.Button(button_frame, text="Register Students", command=self.process_bulk_registration).pack(side=tk.LEFT, padx=5)
        ttk.Button(button_frame, text="Clear", command=lambda: self.bulk_data_text.delete(1.0, tk.END)).pack(side=tk.LEFT, padx=5)
    
    def import_csv_file(self):
        """Import CSV file for bulk registration"""
        file_path = filedialog.askopenfilename(
            title="Select CSV File",
            filetypes=[("CSV Files", "*.csv"), ("All Files", "*.*")]
        )
        
        if file_path:
            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    content = f.read()
                self.bulk_data_text.insert(tk.END, content)
            except Exception as e:
                messagebox.showerror("Error", f"Failed to read file: {e}")
    
    def process_bulk_registration(self):
        """Process bulk student registration from pasted data"""
        data = self.bulk_data_text.get(1.0, tk.END).strip()
        
        if not data:
            messagebox.showerror("Error", "Please paste student data")
            return
        
        lines = data.split('\n')
        success_count = 0
        error_count = 0
        errors = []
        
        try:
            for line_num, line in enumerate(lines, 1):
                line = line.strip()
                if not line:
                    continue
                
                try:
                    parts = [p.strip() for p in line.split(',')]
                    
                    if len(parts) < 4:
                        errors.append(f"Line {line_num}: Invalid format (need Name, Email, Form Class, Student ID)")
                        error_count += 1
                        continue
                    
                    name, email, form_class, student_id = parts[0], parts[1], parts[2], parts[3]
                    
                    if not name or not email or not form_class or not student_id:
                        errors.append(f"Line {line_num}: Missing required fields")
                        error_count += 1
                        continue
                    
                    if email and not self.validate_email(email):
                        errors.append(f"Line {line_num}: Invalid email format for {name}")
                        error_count += 1
                        continue
                    
                    # Check if student ID already exists
                    self.cursor.execute("SELECT id FROM students WHERE student_id = ?", (student_id,))
                    if self.cursor.fetchone():
                        errors.append(f"Line {line_num}: Student ID {student_id} already exists")
                        error_count += 1
                        continue
                    
                    # Insert student
                    self.cursor.execute(
                        "INSERT INTO students (name, email, form_class, student_id) VALUES (?, ?, ?, ?)",
                        (name, email, form_class, student_id)
                    )
                    success_count += 1
                
                except Exception as e:
                    errors.append(f"Line {line_num}: {str(e)}")
                    error_count += 1
            
            self.conn.commit()
            
            # Show results
            message = f"✓ Successfully registered: {success_count} students\n"
            if error_count > 0:
                message += f"✗ Errors: {error_count}\n\n"
                message += "Error Details:\n" + "\n".join(errors[:10])
                if len(errors) > 10:
                    message += f"\n... and {len(errors) - 10} more errors"
            
            messagebox.showinfo("Registration Complete", message)
            
            if success_count > 0:
                self.bulk_data_text.delete(1.0, tk.END)
        
        except sqlite3.Error as e:
            messagebox.showerror("Database Error", f"Error during registration: {e}")
    
    def show_all_students(self):
        """Show all registered students"""
        self.clear_screen()
        
        # Header
        header_frame = ttk.Frame(self.root)
        header_frame.pack(fill=tk.X, padx=20, pady=10)
        ttk.Label(header_frame, text="Student List", font=("Arial", 14, "bold")).pack(side=tk.LEFT)
        ttk.Button(header_frame, text="Back", command=self.show_dashboard).pack(side=tk.RIGHT)
        
        # Content
        content_frame = ttk.Frame(self.root)
        content_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=20)
        
        # Create treeview
        columns = ("Student ID", "Name", "Email", "Form Class", "Date Enrolled")
        self.student_tree = ttk.Treeview(content_frame, columns=columns, height=20)
        self.student_tree.column("#0", width=0)
        self.student_tree.column("Student ID", width=80)
        self.student_tree.column("Name", width=120)
        self.student_tree.column("Email", width=150)
        self.student_tree.column("Form Class", width=80)
        self.student_tree.column("Date Enrolled", width=120)
        
        self.student_tree.heading("#0", text="")
        self.student_tree.heading("Student ID", text="Student ID")
        self.student_tree.heading("Name", text="Name")
        self.student_tree.heading("Email", text="Email")
        self.student_tree.heading("Form Class", text="Form Class")
        self.student_tree.heading("Date Enrolled", text="Date Enrolled")
        
        # Load data
        try:
            self.cursor.execute("SELECT student_id, name, email, form_class, date_enrolled FROM students ORDER BY date_enrolled DESC")
            for row in self.cursor.fetchall():
                self.student_tree.insert("", tk.END, values=row)
        except sqlite3.Error as e:
            messagebox.showerror("Error", f"Failed to load students: {e}")
        
        # Scrollbar
        scrollbar = ttk.Scrollbar(content_frame, orient=tk.VERTICAL, command=self.student_tree.yview)
        self.student_tree.configure(yscroll=scrollbar.set)
        
        self.student_tree.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
    
    def show_marks_management(self):
        """Show student marks registration interface"""
        self.clear_screen()
        
        # Header
        header_frame = ttk.Frame(self.root)
        header_frame.pack(fill=tk.X, padx=20, pady=10)
        ttk.Label(header_frame, text="Student Marks Management", font=("Arial", 14, "bold")).pack(side=tk.LEFT)
        ttk.Button(header_frame, text="Back", command=self.show_dashboard).pack(side=tk.RIGHT)
        
        # Main content
        content_frame = ttk.Frame(self.root)
        content_frame.pack(fill=tk.BOTH, expand=True, padx=20, pady=20)
        
        # Student selection
        ttk.Label(content_frame, text="Select Student:", font=("Arial", 10)).pack(anchor=tk.W, pady=5)
        
        self.student_var = tk.StringVar()
        self.student_combo = ttk.Combobox(content_frame, textvariable=self.student_var, state="readonly", width=50)
        self.student_combo.pack(fill=tk.X, pady=5)
        
        # Load students
        try:
            self.cursor.execute("SELECT id, name, student_id FROM students ORDER BY name")
            self.students_data = {f"{row[2]} - {row[1]}": row[0] for row in self.cursor.fetchall()}
            self.student_combo['values'] = list(self.students_data.keys())
        except sqlite3.Error as e:
            messagebox.showerror("Error", f"Failed to load students: {e}")
        
        # Form for marks entry
        ttk.Label(content_frame, text="Subject:", font=("Arial", 10)).pack(anchor=tk.W, pady=(15, 5))
        self.subject_combo = ttk.Combobox(content_frame, 
                                         values=["Mathematics", "English", "Science", "History", "Geography", "Shona", "Business Studies"], 
                                         state="readonly", width=50)
        self.subject_combo.pack(fill=tk.X, pady=5)
        
        ttk.Label(content_frame, text="Term:", font=("Arial", 10)).pack(anchor=tk.W, pady=(15, 5))
        self.term_combo = ttk.Combobox(content_frame, values=[1, 2, 3], state="readonly", width=50)
        self.term_combo.pack(fill=tk.X, pady=5)
        
        # Marks entry fields
        marks_frame = ttk.Frame(content_frame)
        marks_frame.pack(fill=tk.X, pady=10)
        
        ttk.Label(marks_frame, text="Mark 1:", font=("Arial", 10)).pack(side=tk.LEFT, padx=5)
        self.mark1 = ttk.Entry(marks_frame, width=8)
        self.mark1.pack(side=tk.LEFT, padx=5)
        
        ttk.Label(marks_frame, text="Mark 2:", font=("Arial", 10)).pack(side=tk.LEFT, padx=5)
        self.mark2 = ttk.Entry(marks_frame, width=8)
        self.mark2.pack(side=tk.LEFT, padx=5)
        
        ttk.Label(marks_frame, text="Mark 3:", font=("Arial", 10)).pack(side=tk.LEFT, padx=5)
        self.mark3 = ttk.Entry(marks_frame, width=8)
        self.mark3.pack(side=tk.LEFT, padx=5)
        
        ttk.Label(marks_frame, text="Mark 4:", font=("Arial", 10)).pack(side=tk.LEFT, padx=5)
        self.mark4 = ttk.Entry(marks_frame, width=8)
        self.mark4.pack(side=tk.LEFT, padx=5)
        
        ttk.Label(marks_frame, text="Exam:", font=("Arial", 10)).pack(side=tk.LEFT, padx=5)
        self.exam_mark = ttk.Entry(marks_frame, width=8)
        self.exam_mark.pack(side=tk.LEFT, padx=5)
        
        # Register button
        ttk.Button(content_frame, text="Register Marks", command=self.register_marks).pack(pady=20, fill=tk.X)
        
        # Marks history
        ttk.Label(content_frame, text="Marks History:", font=("Arial", 10, "bold")).pack(anchor=tk.W, pady=(15, 5))
        
        columns = ("Subject", "Term", "Mark 1", "Mark 2", "Mark 3", "Mark 4", "Exam", "Total", "Grade")
        self.marks_tree = ttk.Treeview(content_frame, columns=columns, height=10)
        self.marks_tree.column("#0", width=0)
        
        for col in columns:
            self.marks_tree.column(col, width=50)
            self.marks_tree.heading(col, text=col)
        
        self.marks_tree.pack(fill=tk.BOTH, expand=True, pady=5)
    
    def register_marks(self):
        """Register student marks"""
        if not self.student_var.get():
            messagebox.showerror("Error", "Please select a student")
            return
        
        if not self.subject_combo.get():
            messagebox.showerror("Error", "Please select a subject")
            return
        
        if not self.term_combo.get():
            messagebox.showerror("Error", "Please select a term")
            return
        
        try:
            mark1 = float(self.mark1.get()) if self.mark1.get() else None
            mark2 = float(self.mark2.get()) if self.mark2.get() else None
            mark3 = float(self.mark3.get()) if self.mark3.get() else None
            mark4 = float(self.mark4.get()) if self.mark4.get() else None
            exam = float(self.exam_mark.get()) if self.exam_mark.get() else None
        except ValueError:
            messagebox.showerror("Error", "Please enter valid numbers for marks")
            return
        
        # Calculate total
        marks = [m for m in [mark1, mark2, mark3, mark4, exam] if m is not None]
        total = sum(marks) if marks else 0
        
        # Determine grade
        grade = self.calculate_grade(total / len(marks) if marks else 0)
        
        # Get student ID
        student_id = self.students_data.get(self.student_var.get())
        
        try:
            self.cursor.execute(
                """INSERT INTO student_marks 
                   (student_id, subject, term, mark_1, mark_2, mark_3, mark_4, exam_mark, total_mark, grade)
                   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""",
                (student_id, self.subject_combo.get(), int(self.term_combo.get()), 
                 mark1, mark2, mark3, mark4, exam, total, grade)
            )
            self.conn.commit()
            messagebox.showinfo("Success", "Marks registered successfully!")
            
            # Clear fields
            self.mark1.delete(0, tk.END)
            self.mark2.delete(0, tk.END)
            self.mark3.delete(0, tk.END)
            self.mark4.delete(0, tk.END)
            self.exam_mark.delete(0, tk.END)
            
        except sqlite3.Error as e:
            messagebox.showerror("Database Error", f"Error registering marks: {e}")
    
    def calculate_grade(self, average):
        """Calculate grade based on average"""
        if average >= 90:
            return "A"
        elif average >= 80:
            return "B"
        elif average >= 70:
            return "C"
        elif average >= 60:
            return "D"
        else:
            return "E"
    
    def show_my_marks(self):
        """Show student's own marks"""
        # This would show marks specific to logged-in student
        messagebox.showinfo("Marks", "Your marks will be displayed here")
    
    def logout(self):
        """Logout user"""
        self.current_user = None
        self.current_user_role = None
        self.show_login_screen()
    
    def clear_screen(self):
        """Clear all widgets from screen"""
        for widget in self.root.winfo_children():
            widget.destroy()

if __name__ == "__main__":
    root = tk.Tk()
    app = SchoolManagementSystem(root)
    root.mainloop()
