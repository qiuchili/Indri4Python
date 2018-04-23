from py4j.java_gateway import JavaGateway
gateway = JavaGateway()
# gateway.launch_gateway(port=25333)
indri = gateway.entry_point.getIndriAPI()
TFIDF = indri.getTFIDFRep4Doc(1)
print(TFIDF['score'])

# gateway.entry_point.print()             # connect to the JVM
  # create a java.util.Random instance
# number1 = random.nextInt(10)              # call the Random.nextInt method
# number2 = random.nextInt(10)
# print(number1,number2)
# addition_app = gateway.entry_point        # get the AdditionApplication
# addition_app.addition(number1,number2)
