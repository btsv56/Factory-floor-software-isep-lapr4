<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : relatHTML.xsl
    Created on : 10 de Junho de 2020, 15:41
    Author     : Utilizador
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns1='http://www.dei.isep.ipp.pt/lprog' version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/ns1:relatório">
        <html>
            <head>
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"/>
                <title>SSFM</title>
            </head>
            <body>
                <div class="container">
                    <xsl:apply-templates select="ns1:páginaRosto"/>
                    <xsl:call-template name="Indices"/>
                    <xsl:apply-templates select="ns1:corpo"/>
                    <xsl:apply-templates select="ns1:anexos"/>
                </div>
            </body>
        </html>
    </xsl:template>
    
    <!-- Página Rosto -->
    <xsl:template match="ns1:páginaRosto">
        <xsl:apply-templates select="ns1:logotipoDEI"/>
        <h1 style="color:red">
            <xsl:value-of select="ns1:tema"/>
        </h1>
        <h2>Disciplina de <xsl:value-of select="ns1:disciplina/ns1:designação"/> (<xsl:value-of select="ns1:disciplina/ns1:sigla"/>) do <xsl:value-of select="ns1:disciplina/ns1:anoCurricular"/>º ano curricular</h2>
        <h3>Turma: <xsl:value-of select="ns1:turma"/></h3>
        <h3>Grupo</h3>
        <table border="1">
            <tr>
                <th>Nome</th>
                <th>Número</th>
                <th>Mail</th>
            </tr>
            <xsl:apply-templates select="ns1:autor"/>
        </table>
        <h3>Professores</h3>
        <table border="1">
            <tr>
                <th>Sigla</th>
                <th>Nome</th>
                <th>Tipo de aula</th>
            </tr>
            <xsl:apply-templates select="ns1:professor"/>
        </table>
        <h4>Data: <xsl:value-of select="ns1:data"/></h4>
    </xsl:template>
    
    <xsl:template match="ns1:logotipoDEI">
        <img src="{.}" />
    </xsl:template>
    
    <xsl:template match="ns1:autor">
        <tr>
            <td>
                <xsl:value-of select="ns1:nome"/>
            </td>
            <td>
                <xsl:value-of select="ns1:número"/>
            </td>
            <td>
                <xsl:value-of select="ns1:mail"/>
            </td>
        </tr>
    </xsl:template>
    
    <xsl:template match="ns1:professor">
        <tr>
            <td>
                <xsl:value-of select="@sigla"/>
            </td>
            <td>
                <xsl:value-of select="text()"/>
            </td>
            <td>
                <xsl:value-of select="@tipoAula"/>
            </td>
        </tr>
    </xsl:template>
    
    <!-- Indice -->
    <xsl:template name="Indices">
        <h2>Indice</h2>
        <ol>
            <xsl:apply-templates select="//@id[../@tituloSecção]"/>
        </ol>
    </xsl:template>
    
    <xsl:template match="//@id[../@tituloSecção]">
        <li>
            <xsl:value-of select="../@tituloSecção"/>
            <xsl:if test="../ns1:subsecção">
                <ol>
                    <xsl:for-each select="../ns1:subsecção">
                        <li>
                            <xsl:value-of select="."/>
                        </li>
                    </xsl:for-each>
                </ol>
            </xsl:if>
        </li>
    </xsl:template>

    <!-- Corpo -->
    <xsl:template match="ns1:corpo">
        <h2>
            <xsl:value-of select="ns1:introdução/@tituloSecção"/>
        </h2>
        <xsl:apply-templates select="ns1:introdução"/>
        <h2>
            <xsl:value-of select="ns1:outrasSecções/ns1:análise/@tituloSecção"/>
        </h2>
        <xsl:apply-templates select="ns1:outrasSecções/ns1:análise"/>

        <h2>
            <xsl:value-of select="ns1:outrasSecções/ns1:linguagem/@tituloSecção"/>
        </h2>
        <xsl:apply-templates select="ns1:outrasSecções/ns1:linguagem"/>

        <h2>
            <xsl:value-of select="ns1:outrasSecções/ns1:transformações/@tituloSecção"/>
        </h2>

        <xsl:apply-templates select="ns1:outrasSecções/ns1:transformações"/>
        <h2>
            <xsl:value-of select="ns1:conclusão/@tituloSecção"/>
        </h2>
        <xsl:apply-templates select="ns1:conclusão"/>
        <h2>
            <xsl:value-of select="ns1:referências/@tituloSecção"/>
        </h2>
        <xsl:apply-templates select="ns1:referências/ns1:refBibliográfica"/>
        <xsl:apply-templates select="ns1:referências/ns1:refWeb"/>
    </xsl:template>
    
    <!-- Introdução -->
    <xsl:template match="ns1:introdução">
        <xsl:apply-templates select="node()"/>
    </xsl:template>
    
    <!-- Análise -->
    <xsl:template match="ns1:outrasSecções/ns1:análise">
        <xsl:apply-templates select="node()"/>
    </xsl:template>

    <!-- Linguagens -->
    <xsl:template match="ns1:outrasSecções/ns1:linguagem">
        <xsl:apply-templates select="node()"/>
    </xsl:template>

    
    <!-- Transformações -->
    <xsl:template match="ns1:outrasSecções/ns1:transformações">
        <xsl:apply-templates select="node()"/>
        <br/>
        <img class="img-fluid centered" src="{ns1:figura/@src}" alt="{ns1:figura/@descrição}"/>
        <br/>
        <br/>
    </xsl:template>

    <!-- Conclusão -->
    <xsl:template match="ns1:conclusão">
        <a>
            <xsl:apply-templates select="node()"/>
        </a>
    </xsl:template>
    
    <!-- Referências -->
    <xsl:template match="ns1:refBibliográfica">
        <a>
            Titulo: <xsl:value-of select="ns1:título"/>
            <br/>
            Data publicação: <xsl:value-of select="ns1:dataPublicação"/>
            <br/>
            Autor: <xsl:value-of select="ns1:autor"/>
            <br/>
            <br/>
        </a>
    </xsl:template>
    
    <xsl:template match="ns1:refWeb">
        <a>
            URL: <xsl:value-of select="ns1:URL"/>
            <br/>
            Descrição: <xsl:value-of select="ns1:descrição"/>
            <br/>
            Data de consulta: <xsl:value-of select="ns1:consultadoEm"/>
            <br/>
            <br/>
        </a>
    </xsl:template>
    
    <!-- Anexos -->
    <xsl:template match="ns1:anexos">
        <h3>
            <xsl:value-of select="@tituloSecção"/>
        </h3>
        <xsl:apply-templates select="node()"/>
    </xsl:template>
    
    <!-- Types -->
    <xsl:template match="ns1:parágrafo">
        <xsl:apply-templates select="node()"/>
    </xsl:template>
    
    <xsl:template match="ns1:itálico">
        <i>
            <xsl:value-of select="."/>
        </i>
    </xsl:template>
    
    <xsl:template match="ns1:negrito">
        <b>
            <xsl:value-of select="."/>
        </b>
    </xsl:template>
    
    <xsl:template match="ns1:sublinhado">
        <u>
            <xsl:value-of select="."/>
        </u>
    </xsl:template>
    
    <xsl:template match="ns1:citação">
        (<xsl:value-of select="."/>)
    </xsl:template>

    <xsl:template match="ns1:codigo">
        <xsl:apply-templates select="ns1:bloco"/>
    </xsl:template>

    <xsl:template match="ns1:bloco">
        <pre>
            <code>
                <xsl:value-of select="."/>
            </code>
        </pre>
    </xsl:template>

    <xsl:template match="ns1:subsecção">
        <h3>
            <xsl:value-of select="."/>
        </h3>
    </xsl:template>

    <xsl:template match="ns1:listaItems">
        <ul>
            <xsl:apply-templates select="ns1:item"/>
            <br/>
        </ul>
    </xsl:template>
    
    <xsl:template match="ns1:item">
        <li>
            <xsl:value-of select="."/>
        </li>
    </xsl:template>

</xsl:stylesheet>
