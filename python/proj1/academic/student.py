import sys
from marks import Mark
from meta import meta_handson as M
class Student:
    def __init__(self, name, marks = []):
        self.name = name
        self.marks = marks
    def get_name(self):
        return self.name
    def add_mark(self, mark: Mark):
        self.marks.append(mark)
    def get_marks(self):
        return self.marks

print(sys.path)
s = Student('Tamil')
s.add_mark(Mark('Maths', 100))
s.add_mark(Mark('DS', 90))
print(s.get_marks())
print('done')

m1 = M.MyMeta()
print(m1.NAME)