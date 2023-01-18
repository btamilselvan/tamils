import math

print('Hello')

#name = input("enter name: ")
name = "Tamil"

print('name is ', name)

print(type(name))

num1 = 10
num2 = 10.5

num3 = num1+num2

print(f'total is {num3}')
print("total is ", num3)

flag = True

my_list = [1, 3, "oragne", {"name": "Tamil", "age": 25}]
print(my_list)
my_list.append("apple")
my_list.extend(["apple"])
print(my_list)
print('expanded my_list is', *my_list)

# print(my_list.)
for item in my_list:
    print(f'item is {item}')

my_set = set()
my_set.add(2)
my_set.add(2)
my_set.add(3)
print(f'set is {my_set}')
print('expanded set is', *my_set)

another_set = {1, 2, 3, 3}
print(f'another set is {another_set}')

my_dictionary = {}
my_dictionary['name']='Tamil'
my_dictionary['age']=25
print(f'my_dict is {my_dictionary}')

for key, value in my_dictionary.items():
    print(f'key is {key}, value is {value}')

for item in my_dictionary.items():
    print(f'item is {item}')

my_tuple = (11,2,3)
print(my_tuple)

print(10 * '*')

n1 = 10
n2 = 25

print(float(n2/n1))
print('this does the floor operation', float(n2//n1))
print(int(n2*n1))
print('this does the exponential operation', int(n2**n1))


def my_method1():
    pass

def my_method2(*args):
    print('my method 2')
    print(args)
    print(args[1])
    # print(f'input list is {list}')

def my_method3(**kwargs):
    print(kwargs)
    for key, value in kwargs.items():
        print(f'key is {key}, value is {value}')

my_method2(*my_list)
my_method3(name='Tamil')
my_method3(**my_dictionary)

my_lambda_fun = lambda x: x+5
print(my_lambda_fun(50))

another_lambda_fun = lambda x,y: x+y
print(another_lambda_fun(10, 20))

def multiplier_lambda_fun(num):
    return lambda n: n*num

doubler = multiplier_lambda_fun(2)
print(doubler(14))

tripler = multiplier_lambda_fun(3)
print(tripler(14))

print(math.sqrt(21))

