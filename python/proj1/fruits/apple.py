import sys
import os

print('file is ', __file__)
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
print(SCRIPT_DIR)
print(os.path.dirname(SCRIPT_DIR))
sys.path.append(os.path.dirname(SCRIPT_DIR))

from academic.meta import meta_handson as M

class Apple:
    def __init__(self, name):
        self.name = name
        
    def display(self):
        print("my name is ", self.name)
        return self.name

    def meta_test(self):
        print(M.MyMeta().NAME)

a = Apple("fiji")
print(a.display())
