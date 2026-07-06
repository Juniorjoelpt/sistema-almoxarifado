import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import ConversaoPreview from "../components/ConversaoPreview";

function NovoProduto() {

    const navigate = useNavigate();
    const [categorias, setCategorias] = useState([]);
  
   const [produto, setProduto] = useState({

    codigo: "",
    nome: "",
    categoria: {
        id: ""
    },
    unidadeEstoque: "",
    unidadeEmbalagem: "",
    fatorConversao: 1,
    estoqueAtual: 0,
    estoqueMinimo: 0,
    dataValidade: ""

});

	useEffect(() => {

    carregarCategorias();
   


	}, []);
	async function carregarCategorias() {

    try {

        const response =
            await api.get("/api/categorias");

        setCategorias(response.data);

    } catch (erro) {

        console.error(
            "Erro ao carregar categorias",
            erro
        );
    }
}

   function alterarCampo(e) {

    const { name, value } = e.target;

    if (name === "categoria") {

        setProduto({

            ...produto,

            categoria: {
                id: Number(value)
            }

        });

        return;
    }

  
    setProduto({

        ...produto,

        [name]:
            name === "estoqueAtual" ||
            name === "estoqueMinimo"
                ? Number(value)
                : value

    });
}

    async function salvar(e) {

        e.preventDefault();

        try {
	    console.log(produto);
            await api.post(
                "/api/produtos",
                produto
            );

            alert("Produto cadastrado!");

            navigate("/produtos");

        } catch (erro) {

    console.log("STATUS:");
    console.log(erro.response?.status);

    console.log("BODY:");
    console.log(erro.response?.data);

    console.log("REQUEST:");
    console.log(produto);

    alert(JSON.stringify(erro.response?.data));

}
    }
		const categoriaSelecionada =
    categorias.find(
        c => c.id === produto.categoria?.id
    );

const ehAlimento =
    categoriaSelecionada?.nome
        ?.toLowerCase()
        .includes("alimento");

const gruposUnidadesEstoque = [
{
    grupo: "💊 Medicamentos e Insumos Hospitalares",
    unidades: [

        { value: "UN", label: "UN - Unidade" },
        { value: "CPR", label: "CPR - Comprimido" },
        { value: "CPS", label: "CPS - Cápsula" },
        { value: "AMP", label: "AMP - Ampola" },
        { value: "FA", label: "FA - Frasco-Ampola" },
        { value: "FR", label: "FR - Frasco" },
        { value: "TB", label: "TB - Tubo" },
        { value: "BG", label: "BG - Bisnaga" },
        { value: "ENV", label: "ENV - Envelope" },
        { value: "SACHET", label: "SACHET - Sachê" },
        { value: "RL", label: "RL - Rolo" },
        { value: "PAR", label: "PAR - Par" }

    ]
},

{
    grupo: "🍚 Gêneros Alimentícios",
    unidades: [

        { value: "KG", label: "KG - Quilograma" },
        { value: "L", label: "L - Litro" },
        { value: "PCT", label: "PCT - Pacote" },
        { value: "LTA", label: "LTA - Lata" },
        { value: "PT", label: "PT - Pote" },
        { value: "DZ", label: "DZ - Dúzia" },
        { value: "FD", label: "FD - Fardo" },
        { value: "CX", label: "CX - Caixa" }

    ]
},

{
    grupo: "🪑 Material Permanente",
    unidades: [

        { value: "UN", label: "UN - Unidade" },
        { value: "JG", label: "JG - Jogo" },
        { value: "CJ", label: "CJ - Conjunto" }

    ]
},

{
    grupo: "📁 Material de Escritório",
    unidades: [

        { value: "UN", label: "UN - Unidade" },
        { value: "CX", label: "CX - Caixa" },
        { value: "PCT", label: "PCT - Pacote" },
        { value: "RESMA", label: "RESMA - Resma" },
        { value: "RL", label: "RL - Rolo" }

    ]
}

];

const gruposUnidadesCompra = [ 
{ grupo: "📦 Embalagens do Fornecedor",
 unidades: [ 
{ value: "CX", label: "CX - Caixa" },
 { value: "FD", label: "FD - Fardo" },
 { value: "PCT", label: "PCT - Pacote" },
 { value: "LTA", label: "LTA - Lata" },
 { value: "FR", label: "FR - Frasco" },
 { value: "FA", label: "FA - Frasco-Ampola" },
 { value: "TB", label: "TB - Tubo" },
 { value: "BG", label: "BG - Bisnaga" },
 { value: "RL", label: "RL - Rolo" },
 { value: "ENV", label: "ENV - Envelope" },
 { value: "SACHET", label: "SACHET - Sachê" }


 ]


 },



 { grupo: "⚖️ Embalagens por Peso ou Volume",
 unidades: [


 { value: "KG", label: "KG - Quilograma" },
 { value: "L", label: "L - Litro" } 


]
 
},

 { grupo: "📋 Outros",
 unidades: [ 
{ value: "UN",
 label: "UN - Unidade" },
 { value: "JG", label: "JG - Jogo" },
 { value: "CJ", label: "CJ - Conjunto" },
 { value: "DZ", label: "DZ - Dúzia" },
 { value: "PAR", label: "PAR - Par" },
 { value: "PT", label: "PT - Pote" }, 
 { value: "RESMA", label: "RESMA - Resma" }
 
]

 }

 ];

const gruposUnidades = [


{
    grupo: "💊 Medicamentos e Insumos Hospitalares",
    unidades: [

        { value: "UN", label: "UN - Unidade" },
        { value: "CPR", label: "CPR - Comprimido" },
        { value: "CPS", label: "CPS - Cápsula" },
        { value: "AMP", label: "AMP - Ampola" },
        { value: "FA", label: "FA - Frasco-Ampola" },
        { value: "FR", label: "FR - Frasco" },
        { value: "TB", label: "TB - Tubo" },
        { value: "BG", label: "BG - Bisnaga" },
        { value: "ENV", label: "ENV - Envelope" },
        { value: "SACHET", label: "SACHET - Sachê" },
        { value: "RL", label: "RL - Rolo" },
        { value: "PAR", label: "PAR - Par" }

    ]
},

{
    grupo: "🍚 Gêneros Alimentícios",
    unidades: [

        { value: "KG", label: "KG - Quilograma" },
        { value: "L", label: "L - Litro" },
        { value: "PCT", label: "PCT - Pacote" },
        { value: "LTA", label: "LTA - Lata" },
        { value: "PT", label: "PT - Pote" },
        { value: "DZ", label: "DZ - Dúzia" },
        { value: "FD", label: "FD - Fardo" },
        { value: "CX", label: "CX - Caixa" }

    ]
},

{
    grupo: "🪑 Material Permanente",
    unidades: [

        { value: "UN", label: "UN - Unidade" },
        { value: "JG", label: "JG - Jogo" },
        { value: "CJ", label: "CJ - Conjunto" }

    ]
},

{
    grupo: "📁 Material de Escritório",
    unidades: [

        { value: "UN", label: "UN - Unidade" },
        { value: "CX", label: "CX - Caixa" },
        { value: "PCT", label: "PCT - Pacote" },
        { value: "RESMA", label: "RESMA - Resma" },
        { value: "RL", label: "RL - Rolo" }

    ]
}

];

    return (

        <MainLayout>

            <h1>Novo Produto</h1>

            <form onSubmit={salvar}>

                <div className="mb-3">

                    <label>Código</label>

                    <input
                        type="text"
                        name="codigo"
                        className="form-control"
                        value={produto.codigo}
                        onChange={alterarCampo}
                    />

                </div>

                <div className="mb-3">

                    <label>Nome</label>

                    <input
                        type="text"
                        name="nome"
                        className="form-control"
                        value={produto.nome}
                        onChange={alterarCampo}
                    />

                </div>

                <div className="mb-3">

    <label>Categoria</label>

    <select
        className="form-select"
        value={produto.categoria.id}
        onChange={(e) =>
            setProduto({

                ...produto,

                categoria: {
                    id: Number(e.target.value)
                }

            })
        }
    >

        <option value="">
            Selecione uma categoria
        </option>

        {categorias.map(categoria => (

            <option
                key={categoria.id}
                value={categoria.id}
            >
                {categoria.nome}
            </option>

        ))}

    </select>

</div>

{ehAlimento && (

    <div className="mb-3">

        <label>Data de Validade</label>

        <input
            type="date"
            name="dataValidade"
            className="form-control"
            value={produto.dataValidade}
            onChange={alterarCampo}
        />

    </div>

)}


                <div className="col-md-4 mb-3">

<label>

Unidade de Estoque

</label>


<small className="text-muted d-block mb-2">
 Como o produto será controlado dentro do almoxarifado.
 </small>


<select

className="form-select"

value={produto.unidadeEstoque}

onChange={(e)=>

setProduto({

...produto,

unidadeEstoque:e.target.value

})

}

>

<option value="">
    Selecione
</option>

{

gruposUnidadesEstoque.map(grupo => (

<optgroup
    key={grupo.grupo}
    label={grupo.grupo}
>

{

grupo.unidades.map(unidade=>(

<option
    key={`${grupo.grupo}-${unidade.value}`}
    value={unidade.value}
>

{unidade.label}

</option>

))

}

</optgroup>

))

}
</select>

</div>





	<div className="col-md-4 mb-3">

<label>
Unidade de Compra
</label>

<small className="text-muted d-block mb-2">
 Como o fornecedor entrega este produto na nota fiscal. 
</small>


<select
    className="form-select"
    value={produto.unidadeCompra}
    onChange={(e)=>

        setProduto({

            ...produto,

            unidadeCompra:e.target.value

        })

    }
>

    <option value="">
    Selecione
</option>

{

gruposUnidadesCompra.map(grupo => (

<optgroup
    key={grupo.grupo}
    label={grupo.grupo}
>

{

grupo.unidades.map(unidade=>(

<option
    key={`${grupo.grupo}-${unidade.value}`}
    value={unidade.value}
>

{unidade.label}

</option>

))

}

</optgroup>

))

}

</select>

</div>


<div className="col-md-4 mb-3">

<label>

Unid. por Embalagem

</label>


<small className="text-muted d-block mb-2"> 
Quantas unidades de estoque existem em uma embalagem comprada. 
</small>


<input

type="number"

min="1"

step="1"

className="form-control"

value={produto.fatorConversao}

onChange={(e)=>

setProduto({

...produto,

fatorConversao:Number(e.target.value)

})

}

/>

</div>
		


		<ConversaoPreview

    unidadeCompra={produto.unidadeCompra}

    unidadeEstoque={produto.unidadeEstoque}

    fatorConversao={produto.fatorConversao}

		/>


	

                <div className="mb-3">

                    <label>Estoque Atual</label>

                    <input
                        type="number"
                        name="estoqueAtual"
                        className="form-control"
                        value={produto.estoqueAtual}
                        onChange={alterarCampo}
                    />

                </div>

                <div className="mb-3">

                    <label>Estoque Mínimo</label>

                    <input
                        type="number"
                        name="estoqueMinimo"
                        className="form-control"
                        value={produto.estoqueMinimo}
                        onChange={alterarCampo}
                    />

                </div>

                <button
                    className="btn btn-success"
                >
                    Salvar
                </button>

            </form>

        </MainLayout>
    );
}

export default NovoProduto;