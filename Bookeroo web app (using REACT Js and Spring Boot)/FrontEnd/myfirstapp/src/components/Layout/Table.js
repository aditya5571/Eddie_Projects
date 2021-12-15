import React, {useEffect} from 'react'
import {useTable, useRowSelect} from 'react-table'
import {useDispatch} from "react-redux";
import {login} from "../../actions/securityActions";
import {GET_CATEGORIES, GET_ERRORS, GET_SELECTED_ADS} from "../../actions/types";



const IndeterminateCheckbox = React.forwardRef(

    ({ indeterminate, ...rest }, ref) => {
        const defaultRef = React.useRef()
        const resolvedRef = ref || defaultRef
        const dispatch = useDispatch()

        React.useEffect(() => {
            resolvedRef.current.indeterminate = indeterminate
        }, [resolvedRef, indeterminate])

        return (
            <>
                <input type="checkbox"  ref={resolvedRef} {...rest} />
            </>
        )
    }
)


function Table({ columns, data, onRowSelect }) {


    const dispatch = useDispatch();

    // Use the state and functions returned from useTable to build your UI
    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
        selectedFlatRows,
        state: {selectedRowIds}
    } = useTable({
        columns,
        data,
    },
    useRowSelect,
        hooks => {
            hooks.visibleColumns.push(columns => [
                // Let's make a column for selection
                {
                    id: 'selection',


                    // The cell can use the individual row's getToggleRowSelectedProps method
                    // to the render a checkbox
                    Cell: ({ row }) => {

                        return(
                            <div >
                                <IndeterminateCheckbox  {...row.getToggleRowSelectedProps()}  />
                                {}
                            </div>
                        )
                    },
                },
                ...columns,
            ])

        }
)
    useEffect(() => {
        onRowSelect(selectedFlatRows.map(
            d => d.original.id
        ));
    }, [onRowSelect, selectedFlatRows]);
    // Render the UI for your table
    return (
        <>
        <table {...getTableProps()}>
            <thead>
            {headerGroups.map(headerGroup => (
                <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map(column => (
                        <th {...column.getHeaderProps()}>{column.render('Header')}</th>
                    ))}
                </tr>
            ))}
            </thead>
            <tbody {...getTableBodyProps()}>
            {rows.map((row, i) => {
                prepareRow(row)
                return (
                    <tr {...row.getRowProps()}>
                        {row.cells.map(cell => {
                            return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                        })}
                    </tr>
                )
            })}
            </tbody>
        </table>
        </>
    )
}
export default Table

