#!/usr/bin/env python3
"""
Installation and setup script for School Management System
"""

import os
import sys
import subprocess

def install_dependencies():
    """Install required Python packages"""
    print("Checking Python dependencies...")
    
    # tkinter is usually bundled, but we'll check
    try:
        import tkinter
        print("✓ tkinter is installed")
    except ImportError:
        print("✗ tkinter not found. Installing...")
        if sys.platform == "linux":
            os.system("sudo apt-get install python3-tk")
        elif sys.platform == "darwin":
            os.system("brew install python-tk")
        print("Please install tkinter and run this script again")
        return False
    
    print("✓ All dependencies are satisfied")
    return True

def main():
    print("=" * 50)
    print("School Management System - Setup")
    print("=" * 50)
    
    # Check Python version
    if sys.version_info < (3, 7):
        print("✗ Python 3.7 or higher required")
        sys.exit(1)
    
    print(f"✓ Python {sys.version_info.major}.{sys.version_info.minor} detected")
    
    # Install dependencies
    if not install_dependencies():
        sys.exit(1)
    
    print("\n✓ Setup complete!")
    print("To start the application, run: python school_management_system.py")

if __name__ == "__main__":
    main()
