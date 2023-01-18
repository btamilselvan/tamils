#Should inherit from 'type' to be a meta class
class MataCreator(type):
    #this will be called before init method
    def __new__(self, class_name, bases, attrs):
        print(attrs)
        modified_attrs = {}
        for name, value in attrs.items():
            print(name, value)
            if name.startswith('__'):
                modified_attrs[name] = value
            else:
                modified_attrs[name.upper()] = value
        return  type(class_name, bases, modified_attrs)
    def __init__(self):
        pass

class MyMeta(metaclass=MataCreator):
    name = 'MyMeta'
    id = 10

    def display(self):
        print(self.name)

m = MyMeta()
print(m.NAME)

