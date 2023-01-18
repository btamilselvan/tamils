print("meta classes test")

class BaseTest:
    def show(self):
        print("hello")

class Test:
     pass

def display(self):
    print('my name is', self.name)

t = type('Test', (), {"name": 'Tamil'})
Test1 = type('Test1', (BaseTest,), {"name": 'Tamil', 'display': display})
t1 = Test1()

print(t())
print(Test())
print(Test1())
print(t.name)
t1.show()
t1.display()
