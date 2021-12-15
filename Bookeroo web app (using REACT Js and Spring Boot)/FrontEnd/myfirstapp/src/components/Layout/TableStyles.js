import styled from 'styled-components'

export const Styles = styled.div`
  padding: 1rem;
  display: flex;
  justify-content: center;

  table {
    border-spacing: 0;
    border: 1px solid black;
    width: 80%;

    tr {
        text-align: center;
      :last-child {
        td {
          border-bottom: 0;
        }
      }
    }

    th,
    
    td {
      margin: 0;
      padding: 0.5rem;
      border-bottom: 1px solid black;
      border-right: 1px solid black;

      :last-child {
        border-right: 0;
      }
    }
  }
`