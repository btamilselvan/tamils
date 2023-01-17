class Apple:
    def __init__(self, name):
        self.name = name
        
    def display(self):
        print("my name is ", self.name)
        return self.name

a = Apple("fiji")
print(a.display())