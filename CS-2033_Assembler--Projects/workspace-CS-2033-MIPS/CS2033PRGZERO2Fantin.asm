.data
     PROMPT: .ascii "Enter a Number: "
     DATA: .space 12
     FAIL_MSG_1: .asciiz "Input Error: NON-INTEGER FOUND"
     FAIL_MSG_2: .asciiz "\nInput Error: OVERFLOW"
.text
     MAIN:
          # Ask user for an integer
          la $a0, PROMPT
          li $v0, 4
          syscall
   	  
   	  # Get the inputed string
          la $a0, DATA
          li $a1, 12
          li $v0, 8
          syscall
          
          addi $t3, $zero, 2147483647
          subi $t4, $zero, 2147483647
          addi $s2, $zero, 1
          add $s1, $zero, $zero
          la $s0, ($a0)
          lb $t0, 0($s0)
          beq $t0, 45, NEGATIVE # Test if it is negative
          
     LOOP:
          addi $t2, $t2, 1 # Counter for how many times the loop runs
          lb $t0, 0($s0)
          

          
          beq $t0, 0, DONE # Test if it is null
          beq $t0, 10, DONE # Test if it is a new line
          
          slt $t1, $s1, $t3
          beq $t1, 0, FAIL_2
          slt $t1, $s1, $t4
          beq $t1, 1, FAIL_2
          
          beq $t2, 11, FAIL_2 # If the input goes over the max amount of digits possible
          mul $s1, $s1, 10
          slti $t1, $t0, 48 # Test if character value is less than 0
          beq $t1, 1, FAIL_1 # If it is: FAIL
          slti $t1, $t0, 58 # Test if the value is greater than 9
          beq $t1, 0, FAIL_1 # If it is: FAIL
          sub $t0, $t0, 48
          add $s1, $s1, $t0
          addi $s0, $s0, 1
          
          j LOOP
          
     NEGATIVE:
          subi $s2, $zero, 1
          addi $s0, $s0, 1
          
          j LOOP
          
     FAIL_1: # Non-integer error
     	  la $a0, FAIL_MSG_1
          li $v0, 4
          syscall
     	  
     	  j EXIT
          
     FAIL_2: # Overflow error
          la $a0, FAIL_MSG_2
          li $v0, 4
          syscall
     	  
     	  j EXIT
     	  
     DONE:
          mul $s1, $s1, $s2
          add $a0, $s1, $zero
          li $v0, 1
          syscall
          
          j EXIT
          
     EXIT: