package com.insiwd.literalura.model;

import java.util.List;

public record DadosApi(
                       int count,
                       String next,
                       String previous,
                       List<DadosLivro> results) {
}
