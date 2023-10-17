package com.example.monday.data;

import java.util.UUID;


public record Student (UUID id, String name, StudentUnit unit, Long index){

}
