from apple import Apple

class Orange:
    def __init__(self, name):
        self.name = name
        
    def display(self):
        print("my name is ", self.name)
        return self.name

o = Orange("kasgmiri")
print(o.display())

a = Apple("Cuban")
print(a.display())

