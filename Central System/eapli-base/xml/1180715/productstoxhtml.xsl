<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Products</h2>
                <table border="1">
                    <tr>
                        <th>Fabrication Code</th>
                        <th>Comercial Code</th>
                        <th>Brief Description</th>
                        <th>Complete Description</th>
                        <th>Product Category</th>
                        <th>Unity</th>
                        <th>Production Sheet</th>
                    </tr>
                    <xsl:for-each select="SSFM/Products/Product">
                        <tr>
                            <td><xsl:value-of select="./@fabricationCode"/></td>
                            <td><xsl:value-of select="comercialCode"/></td>
                            <td><xsl:value-of select="briefDescription"/></td>
                            <td><xsl:value-of select="completeDescription"/></td>
                            <td><xsl:value-of select="productCategory"/></td>
                            <td><xsl:value-of select="unity"/></td>
                            <td><xsl:value-of select="productionSheet"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
                <h2>Production Sheets</h2>
                <table border="1">
                    <tr>
                        <th>Fabrication Code</th>
                        <th>Comercial Code</th>
                        <th>Brief Description</th>
                        <th>Complete Description</th>
                        <th>Product Category</th>
                        <th>Unity</th>
                        <th>Production Sheet</th>
                    </tr>
                    <xsl:for-each select="SSFM/Products/Product">
                        <tr>
                            <td><xsl:value-of select="./@fabricationCode"/></td>
                            <td><xsl:value-of select="comercialCode"/></td>
                            <td><xsl:value-of select="briefDescription"/></td>
                            <td><xsl:value-of select="completeDescription"/></td>
                            <td><xsl:value-of select="productCategory"/></td>
                            <td><xsl:value-of select="unity"/></td>
                            <td><xsl:value-of select="productionSheet"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>