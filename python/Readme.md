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

