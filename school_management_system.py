import sqlite3
import tkinter as tk
from tkinter import messagebox, ttk
from datetime import datetime
import re

# =====================================================================
# OFFLINE SCHOOL MANAGEMENT SYSTEM
# =====================================================================

class SchoolManagementSystem:
    def __init__(self, root):
        self.root = root
        self.root.title("School Management System")
        self.root.geometry("900x700")
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
                    user_id INTEGER NOT NULL,
                    form_class TEXT,
                    student_id TEXT UNIQUE,
                    FOREIGN KEY(user_id) REFERENCES users(id)
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
            ttk.Button(menu_frame, text="Manage Timetable", width=40).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="Manage Roster", width=40).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="Manage Users", width=40).pack(pady=5, fill=tk.X)
        
        elif self.current_user_role == "Teacher":
            ttk.Button(menu_frame, text="View My Timetable", width=40).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="View Roster", width=40).pack(pady=5, fill=tk.X)
        
        elif self.current_user_role == "Student":
            ttk.Button(menu_frame, text="View My Classes", width=40).pack(pady=5, fill=tk.X)
            ttk.Button(menu_frame, text="View Timetable", width=40).pack(pady=5, fill=tk.X)
    
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
