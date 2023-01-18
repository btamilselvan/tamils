class Vehicle:
    my_class_var = 0
    
    @staticmethod
    def my_static_method(num1, num2):
        print('this is a static method')
        return num2*num1

    @classmethod
    def my_class_method(cls):
        print('class variable is ', cls.my_class_var)
        print('this is a class method')
    
    def __init__(self, name, price):
        self.name=name
        self.price=price
        Vehicle.my_class_var += 1
    def drive(self):
        print('do not know how to drive')
    def get_price(self):
        return self.price
    def get_name(self):
        return self.name

class Car(Vehicle):

    def __init__(self, name, price, model):
        super().__init__(name, price)
        self.model = model

    def get_model(self):
        return self.model;

    def drive(self):
        print('this is car and I know how to drive')
    def get_price(self):
        return self.price

class MotorCycle(Vehicle):
    def drive(self):
        print('this is motorcycle and I know how to drive')

v = Vehicle('Vehicle', None)
v.drive()

c = Car('TLX', 32000, 'Acura')
c.drive()
print(f'car price is {c.get_price()}')
print(f'car model is {c.get_model()}')

m = MotorCycle('Pulsar', 2000)
m.drive()

print('vehicle class var is ', Vehicle.my_class_var)
Vehicle.my_class_var = 20
print('vehicle class var is ', c.my_class_var)

Vehicle.my_class_method()
print(Vehicle.my_static_method(1,10))