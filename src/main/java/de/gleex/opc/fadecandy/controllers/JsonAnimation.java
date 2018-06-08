package de.gleex.opc.fadecandy.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class JsonAnimation {
    private Map<String, String> pixels;
}
