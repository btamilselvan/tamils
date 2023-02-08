##
- Make sure the intrepreter can find the modules via path (sys.path) when importing modules.
- When needed, the path can be appeneded to the sys.path (refer apple.py)
- Dunder methods / magic method
- Meta classes can be useful to control the class object creation, to enforce constraints, etc,. (see meta package)
- Classes are also objects and created by a special class called 'type'.
- There are severals ways to generate the bytecode for a module. One of the ways is using py_compile module. The generate byte code will be available under __pycache__ directory.
```
    python -m py_compile entry_point.py
```
- The human-readable version of bytecode can be generated using dis module,
```
    python -m dis entry_point.py
```
### Modules
- A python module can be installed and upgraded using,
```
    python -m pip install SomePackage
    python -m pip install --upgrade SomePackage
```
- Passing the --user option to python -m pip install will install a package just for the current user, rather than for all users of the system.
#### Pip
- pip is a standard package manager used to install and maintain packages for Python.
- It is important that the pip version is compatible with the Python version
- Python only started bundling pip with Python 3.4. For earlier versions, pip needs to be “bootstrapped” as described in the Python Packaging User Guide.
- pip install --upgrade pip
- python -m pip --version  or pip --version
- pip3 is an updated version of pip which is used basically for python 3+.

### venv
- A virtual environment is a tool that helps to keep dependencies required by different projects separate by creating isolated python virtual environments for them.
- The venv module supports creating lightweight “virtual environments”, each with their own independent set of Python packages installed in their site directories. A virtual environment is created on top of an existing Python installation, known as the virtual environment’s “base” Python, and may optionally be isolated from the packages in the base environment, so only those explicitly installed in the virtual environment are available.
- When used from within a virtual environment, common installation tools such as pip will install Python packages into a virtual environment without needing to be told to do so explicitly.
- It comes with the python installation.
- How to create and activate an virtual environment? 
```
    Create:
    python -m venv /path/to/the/vm
    e.g. python -m venv c:\Python\venv\python-3111

    Activate:
    source <venv>/bin/activate
    windows: C:\> <venv>\Scripts\activate.bat
    e.g. c:\Python\venv\python-3111\Scripts\activate.bat

    Deactivate using 'deactivate'
```

### Requests
- The Requests module is used to send HTTP requests with ease.
- pip3 install requests
-  https://github.com/request/request https://pypi.org/project/requests/
- 

### Django
- Django is more of a framework than it is a module because of the great features and tools that come with it.
- Django is a high-level Python web framework, it’s very heavyweight.
- Django can be used with other languages and frontend frameworks like React JS, Angular JS, and Vue JS.

### Flask
- Django, FAST API and Flask work similarly for basic websites although Flask is much easier and faster to get up and running with.
- It’s a much lighter-weight web framework and doesn’t come with all of the tools and complex features that are included in Django.
- If you are trying to make a very serious or complex website that includes complex user authentication and authorization then you should probably use Django but if you’re doing more of a side project or a simple website then you should pick Flask because Flask is much easier to work with.

### NumPy
- NumPy is used for:
```Advanced array operations (e.g. add, multiply, slice, reshape, index).
Comprehensive mathematical functions.
Random number generation.
Linear algebra routines.
Fourier transforms, etc.
```
### urllib3
- urllib3 is another user-friendly HTTP client for Python. urllib3 provides many critical features missing from the standard libraries:
```
Thread safety.
Connection pooling.
Retrying requests.
Dealing with HTTP redirects.
Full test coverage.
```
