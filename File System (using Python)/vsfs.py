#!/usr/bin/env python

"""
Python Script for FILE SYSTEM management

@author: Aditya Vadgama, s3845898
"""


import sys
import fileinput
import os.path, time, stat
from pathlib import Path
import collections
# import grp
# import pwd


LAST_ELEMENT = -1
ZERO = 0
ONE = 1
TWO = 2
THREE = 3
FOUR = 4



"""
Helper functions for validation of input arguments
"""

def read_FS(FS):
    file_system = open(FS, "r")
    FS_content = file_system.readlines()
    file_system.close()
    return FS_content


# check if the FS name is valid and contains standard version number on first line
def check_FS_validity(FS):
    split_string = FS.split(".")
    if (split_string[LAST_ELEMENT] != "notes"):
        sys.exit(f"Error! Invalid File System extension.\nExit Code: {ONE}")
    
    Lines = read_FS(FS)
    
    if (Lines[0] != "NOTES V1.0\n"):
        sys.exit(f"Error! Valid NOTES version not present.\nExit Code: {ONE}")


# check if the internal directory already exists in the FS
def exists_ID(FS, ID):
    Lines = read_FS(FS)
    
    # if the directory does not exist, return true
    for line in Lines:
        if (line[0] == "="):
            line = line.rstrip()
            
            if (f"={ID}/" == line):
                return True
    
    return False


# check if the Internal Directory name contains the acceptable characters
def check_ID_validity(FS, ID):
    if (ID.isalnum()):
        return True
    elif ("/" in ID):
        return True
    else:
        sys.exit(f"Invalid directory name! Please use standard characters.\nExit Code: {ONE}")


# check if the internal file already exists in the FS
def check_IF_validity(FS, IF):
    
    # firstly check if given Internal File name is valid
    if ((IF[0] == "/") or (IF[0] == ".")):
        sys.exit(f"Invalid Internal File name.\nExit Code: {ONE}")
    
    Lines = read_FS(FS)
    
    # check for every line in the FS, return True if file exists
    for line in Lines:
        if (line[0] == "@"):
            if (f"@{IF}\n" == line):
                return True
    
    print("Internal file does not exist.")
    return False



"""
Functions for listing attributes of FS
"""

# Function to get the all main files in the FS
def get_FS_content(FS):
    Lines = read_FS(FS)

    # we read from the line after the NOTES V1.0    
    Lines = Lines[1:]
    
    # return a list of all files that don't have '#' in front
    FS_content = []
    for line in Lines:
        if (line[0] != "#"):
            line = line.rstrip()
            if (line[0] == "@") or (line[0] == "="):
                FS_content.append(line)

    return FS_content


# Function to get the creation date and time of the FS
def get_FS_date_time(FS):
    # strip the 'day' from the string
    date_time = time.ctime(os.path.getctime(FS))
    date_time = date_time.split()
    date_time = date_time[1:]
    
    # rejoin the string to get the required date_time format
    modified_date_time = ""
    for element in date_time:
        modified_date_time += f"{element} "
    
    modified_date_time = modified_date_time.rstrip()
    return modified_date_time


# Function to get the size of FS in bytes
def get_FS_bytes(FS):
    return os.path.getsize(FS)


# Function to get the owner name of the FS
def get_FS_owner(FS):
    stat_info = os.lstat(FS)
    user = pwd.getpwuid(stat_info.st_uid)[0]
    return user


# Function to get the group name of the FS
def get_FS_group(FS):
    stat_info = os.lstat(FS)
    group = grp.getgrgid(stat_info.st_gid)[0]
    return group


# Function to get the number of hard links (sub folders) of all content in the FS
def get_FS_hard_link(FS_content, FS):
    dir_count = 0

    # for a file, simply return 1
    if (FS_content[0] == "@"):
        dir_count = 1
    
    # for directories, read each line, find directories that contain more than one "/",
    # since those will be sub-directories, and then increase the counter for each finding
    elif (FS_content[0] == "="):
        Lines = read_FS(FS)
        
        for line in Lines:
            if (line[0] == "="):
                line = line.rstrip()                
                line_to_split = line[0:LAST_ELEMENT]
                
                split_string = line_to_split.split("/")
                split_string = split_string[0:LAST_ELEMENT]
                
                parent_dir = ""
                for element in split_string:
                    parent_dir += element
                    parent_dir += "/"
                
                if (parent_dir == FS_content) and (line.count("/") == (FS_content.count("/") + ONE)):
                    dir_count += 1

    return dir_count


# Function to get permissions list of the FS
def get_FS_permissions(file, FS):
    permissions = str(stat.filemode(os.stat(FS).st_mode))
    
    # design the permissions string according to the required format if the file is a directory
    if (file[0] == "="):
        permissions = "d" + permissions[1:]
    
    return permissions


# Main function for the "list" command
def FS_list(FS):
    
    all_files = get_FS_content(FS)
    date_time = get_FS_date_time(FS)
    file_bytes = get_FS_bytes(FS)
    group_name = get_FS_group(FS)
    owner_name = get_FS_owner(FS)

    # print information according to the required format for each file
    for file in all_files:
        hard_link = get_FS_hard_link(file, FS)
        permissions = get_FS_permissions(file, FS)
        
        file = file[1:]
        
        print(f"{permissions} {hard_link} {owner_name} {group_name} {file_bytes} {date_time} {file}")



"""
Helper functions for copyin, copyout and mkdir
NOTE: DI LEVEL IMPLEMENTED
"""

# Function to automatically generate all the non-existent intermediate directories
def create_intermediate_directories(FS, IF):
    make_directories = True
    while (make_directories):
        # split the string and get the full parent directory of the current IF
        # meaning, if the IF = dir9/dir8/dir7, then we split and fetch dir9/dir8
        if (IF.count("/") > ZERO):
            split_string = IF.split("/")
            split_string = split_string[0:LAST_ELEMENT]
            
            parent_dir = ""
            for element in split_string:
                parent_dir += element
                parent_dir += "/"
            
            parent_dir = parent_dir[0:LAST_ELEMENT]
            
            # now append the directory we extracted to the file, it it didn't exist already
            if not (exists_ID(FS, parent_dir)):
                file_system = open(FS, "a")
                file_system.write(f"={parent_dir}/\n")
                file_system.close()
                print(f"Successfully created directory {parent_dir} in FS")

            IF = parent_dir

        # check this new directory recursively until we reach the base dir, which won't contain any "/"
        if (IF.count("/") == ZERO):
            make_directories = False


# Function to replace the exiting Internal File content, used with the copyin command
def delete_FS_file_content(FS, IF):
    
    # replace all the lines in the FS
    # 1) If pointer has reached the pre-exiting Internal File, delete the File name and activate bool
    # variable so we can check its content next
    # 2) Otherwise, if we're checking some file's content, we must not delete if the line's first
    # character starts with "@", "=" or "#"
    # 3) Else, simply replace the original line in the FS without any modification
    
    check_file_content = False
    for line in fileinput.FileInput(FS, inplace = 1):
        if (f"@{IF}\n" == line):
            line = line.rstrip()
            line = line.replace(line, '#' + line)
            
            # set to true because now we start deleting the Internal File's content
            check_file_content = True

        # delete the internal file's content 
        elif (check_file_content):
        
            # override the file's content until reaching a different file or directory
            if ((line[0] != "@") and (line[0] != "=")):

                if (line[0] == "#"):
                    line = line.rstrip()
                    line = line.replace(line, line)
                else:
                    line = line.rstrip()
                    line = line.replace(line, '#' + line[1:])
            
            else:
                check_file_content = False

        line = line.rstrip()
        print(line)



# Main function for the "copyin" command
def FS_copyin(FS, EF, IF):
    
    # when the given internal file already exists, we comment all of its content and replace it with new one
    if (check_IF_validity(FS, IF)):
        delete_FS_file_content(FS, IF)
    
    else:
        create_intermediate_directories(FS, IF)
    
    # when the Internal file is a new file, we simply append it to the VSFS
    file_system = open(FS, "a")
    external_file = open(EF, "r")
    external_file_content = external_file.readlines()
    
    file_system.write(f"@{IF}\n")
    
    # only write 255 characters on one line
    line_counter = 0
    char_counter = 0
    write = True
    for line in external_file_content:
        file_system.write(' ')
        while (write):
            if (char_counter == 255):
                file_system.write("\n ")
                char_counter = 0
            
            if (line_counter == (len(line) - 1)):
                write = False
            
            file_system.write(line[line_counter])
            line_counter += 1
            char_counter += 1
        
        line_counter = 0
        char_counter = 0
        write = True
    
    file_system.write("\n")
    
    file_system.close()
    external_file.close()
    print(f"Successfully appended the external content")



# Main function for the "copyout" command
def FS_copyout(FS, IF, EF):
    Lines = read_FS(FS)
    
    # read each line, and upon reading the existing Internal File, copy all of its content into another list
    file_content = []
    grab_file_content = False
    if (check_IF_validity(FS, IF)):
        for line in Lines:
            if (f"@{IF}\n" == line):
                grab_file_content = True
            
            elif (grab_file_content):
                
                if ((line[0] != "@") and (line[0] != "=")):
                    
                    if (line[0] != "#"):
                        file_content.append(line[1:])
                
                else:
                    grab_file_content = False
        
        # transfer the file's content to the External File
        external_file = open(EF, "w")
        
        for line in file_content:
            external_file.write(line)
        
        external_file.close()
        print(f"Successfully exported the contents of {IF} to {EF}")
    
    else:
        sys.exit(f".\nExit Code: {ONE}")



# Main function for the "mkdir" command
def FS_mkdir(FS, ID):
    
    # check if given directory ID is valid
    if (check_ID_validity(FS, ID)):

        if (ID[0] == "/") or (ID[LAST_ELEMENT] == "/"):
            sys.exit(f"Invalid directory name.\nPlease don't use '/' at the start or end.\nExit Code: {ONE}")
        
        # ensure that ID does not already exist in the FS, then simply create all
        # intermediate directories plus the given ID
        if not (exists_ID(FS, ID)):
            create_intermediate_directories(FS, ID)
            
            file_system = open(FS, "a")
            file_system.write(f"={ID}/\n")
            file_system.close()
            print(f"Successfully created directory {ID} in FS")

        else:
            sys.exit(f"Error! Given directory ID already exists.\nExit Code: {ONE}")



# Main function for the "rm" command
def FS_rm(FS, IF):
    
    # if the given Internal File is present, append a '#' to the front of the file inside FS
    if (check_IF_validity(FS, IF)):
        delete_FS_file_content(FS, IF)
        print(f"Successfully deleted {IF} in FS")
    
    else:
        sys.exit()



# Main function for the "rmdir" command
def FS_rmdir(FS, ID):
    
    # check if given directory ID name is valid
    if (ID[0] == "/"):
        sys.exit("Invalid directory name")
    
    # check if ID exists in the FS, and delete all of its content
    if(exists_ID(FS, ID)):
        
        for line in fileinput.FileInput(FS, inplace = 1):
            if (f"={ID}/" in line):
                line = line.rstrip()
                line = line.replace(line, '#' + line)
            line = line.rstrip()
            print(line)
            
        print(f"Successfully removed {ID} in FS")
    
    else:
        sys.exit(f"Error! Given directory ID does not exist.\nExit Code: {ONE}")



"""
Helper functions for Defragmentation
"""


def recursive_content_listing(arr, recursion_count, defrag_file_system):
    for line in arr:
        if ((line[0] == "@") and (line.count("/") == recursion_count)):
            defrag_file_system.writelines(line)

        elif ((line[0] == "=") and (line.count("/") == (recursion_count + ONE))):
            defrag_file_system.writelines(line)



# Main function for the "defrag" command
def FS_defrag(FS):
    Lines = read_FS(FS)
    
    defrag_file_system = open(f"defragmented_{FS}", "a")

    # remove the commented lines and add all the valid records to a new list
    defrag_content = []
    for line in Lines:
        if (line[0] != "#"):
            defrag_content.append(line)
    
    # first part - list all base level one content
    defrag_file_system.writelines(Lines[0])
    full_defrag_listing = []
    for line in defrag_content:
        if (line[0] == "="):
            if (line.count("/") == ONE):
                defrag_file_system.writelines(line)
                full_defrag_listing.append(line)
        
        elif (line[0] == "@"):
            if (line.count("/") == 0) and (line[0] != " "):
                defrag_file_system.writelines(line)
                full_defrag_listing.append(line)
    
    defrag_file_system.writelines("\n")
    
    # second part - list all content of the subdirectories
    newArr = []
    for directory in full_defrag_listing:
        
        if (directory[0] == "="):
            directory = directory.rstrip()
            
            # compare the directory to every directory in the FS and fetch all of directory's content within
            for line in defrag_content:
                split_string = line.split("/")
                dir_to_match = split_string[0]

                # check if the main base directory matches, and there is
                # at least one more level of directory within
                if (line[0] == "="):
                    if ((f"{dir_to_match}/" == directory) and (line.count("/") > ONE)):
                        
                        newArr.append(line)
                    
                # for files, simply check if the main base directory matches
                if (line[0] == "@"):
                    filename = split_string[0]
                    if (filename[1:] == directory[1:LAST_ELEMENT]):
                        newArr.append(line)
            
            recursion_count = 1
            recursive_content_listing(newArr, recursion_count, defrag_file_system)
            
            newArr = []
    
    defrag_file_system.close()
    
    print(f"Successfully created defragmented_{FS}")



def main():
    command = sys.argv[ONE]
    FS = sys.argv[TWO]
    
    check_FS_validity(FS)
    
    if (command == "list"):
        FS_list(FS)
    
    elif (command == "copyin"):
        EF = sys.argv[THREE]
        IF = sys.argv[FOUR]
        FS_copyin(FS, EF, IF)
    
    elif (command == "copyout"):
        IF = sys.argv[THREE]
        EF = sys.argv[FOUR]
        FS_copyout(FS, IF, EF)
    
    elif (command == "mkdir"):
        ID = sys.argv[THREE]
        FS_mkdir(FS, ID)

    elif (command == "rm"):
        IF = sys.argv[THREE]
        FS_rm(FS, IF)

    elif (command == "rmdir"):
        ID = sys.argv[THREE]
        FS_rmdir(FS, ID)

    elif (command == "defrag"):
        FS_defrag(FS)

    elif (command == "index"):
        print("Not required as mentioned by Mr Ron")

    else:
        sys.exit("Error! Please input a valid command\n"
              + f"Usage: vsfs.py command FS tags.\nExit Code: {ONE}")


if __name__ == '__main__':
    main()
    
    print(f"Exit code: {ZERO}")

