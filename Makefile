JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	NumberPartition.java \
	MaxHeap.java \
	FileGenerator.java \
	DSUtil.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class